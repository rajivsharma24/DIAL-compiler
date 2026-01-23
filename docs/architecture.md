# Architecture: DIAL Compiler (WebAssembly backend)

This document describes the high-level architecture of the DIAL compiler: the compilation pipeline, internal representations, and the WebAssembly (WAT/WASM) backend/runtime assumptions.

## 1. Goals and scope

The DIAL compiler is a full pipeline compiler for a custom language (DIAL), producing WebAssembly Text (WAT) that can be converted to WASM and executed with a small Node.js runtime layer.

Key goals:

* End-to-end compilation: lexical + syntactic + semantic analysis + code generation.
* A non-trivial language feature set (modules/import/export, records, pointers and dynamic allocation, case/when/default, ternary operator, etc.).
* A backend that targets WebAssembly with a consistent runtime memory model (stack/heap) and explicit error handling.

## 2. Repository map (conceptual)

The project is organized around four main concerns:

* Frontend definitions:

  * Lexer specification (JFlex)
  * Grammar specification (CUP)

* Compiler implementation (Java):

  * AST node types (syntax tree)
  * Name binding / symbol environment
  * Type system and type checking
  * Code generation (WAT emission)

* Runtime / execution:

  * WebAssembly prelude/epilogue (memory helpers, traps, I/O glue)
  * WAT → WASM conversion tooling
  * Node.js runner that instantiates the module and provides imports

* Examples:

  * DIAL source programs used as a regression and feature test suite

## 3. Compilation pipeline

The compiler follows a standard multi-pass architecture:

1. Lexing (JFlex)

* Converts input characters into tokens.
* Handles identifiers, literals, keywords, punctuation and operator tokens.

2. Parsing (CUP)

* Consumes tokens and produces the AST.
* Enforces grammar structure and precedence.
* Recovers from syntax errors when possible.

3. AST construction

* The parser builds strongly-typed AST nodes.
* AST nodes preserve source location metadata (recommended for good diagnostics).

4. Name binding (identifier resolution)

* Creates and manages environments (symbol tables) per scope:

  * module/global scope
  * function scope
  * block scope
  * record fields and local declarations
* Resolves identifiers to declarations (variables, functions, types, modules).
* Detects errors:

  * duplicate definitions in scope
  * undefined identifiers
  * illegal exports/imports or visibility violations (module system)

5. Type checking

* Validates expressions, statements and declarations against the type rules:

  * primitives and composite types (arrays, records, aliases/enums)
  * function signatures and return paths
  * operator typing (including ternary ?:)
  * designator correctness (l-values, field access, indexing, dereference, etc.)
  * assignment compatibility and implicit constraints (where allowed)
* Ensures well-typed programs before code generation.

6. Code generation (WebAssembly Text)

* Walks the typed AST and emits WAT:

  * data layout for globals and constants
  * function bodies with a defined calling convention
  * stack frame management (locals/temporaries)
  * heap allocation for dynamic constructs (`new` / `del`)
  * runtime checks (bounds checks, null checks, out-of-memory)
* Produces `code/codigo.wat` as the primary backend artifact.

7. Execution packaging (WAT → WASM → Node runner)

* `wat2wasm` converts generated WAT to WASM.
* Node runner instantiates the module and provides imports (I/O, error/trap handling).

## 4. Internal representations

### 4.1 AST

The AST is the central IR for the compiler. Nodes typically implement:

* structural children access (for traversals)
* optional “bind” metadata (symbol references)
* optional “type” metadata (inferred/checked type)
* a codegen method or a visitor-based traversal method

Recommended invariant:

* After name binding, every identifier node in expressions/designators points to a declaration symbol.
* After type checking, every expression node has a valid type.

### 4.2 Symbol environment

The compiler uses a scoped environment (stack of symbol tables) to:

* model lexical scoping
* support modules/import/export
* enable robust diagnostics for shadowing/duplicate definitions

### 4.3 Type system model

DIAL’s type system includes:

* base types (and literals)
* arrays (including nested arrays)
* records
* pointer types
* type aliases / user-defined types (as specified)
* function types via signatures

Type checking enforces:

* expression validity (operators, calls, indexing, dereference)
* assignment compatibility
* control-flow constraints (e.g., return consistency)
* designator correctness (what is assignable and what is not)

## 5. WebAssembly backend design

### 5.1 Why WebAssembly

WebAssembly provides:

* a compact, portable target
* structured control flow
* linear memory for implementing language-level stack/heap models
* an ecosystem of tooling (wat2wasm, Node execution)

### 5.2 Runtime memory model (linear memory)

The generated code assumes a linear memory model with:

* stack pointer (SP): points to the current top of stack
* mark/frame pointer (MP): supports stable addressing inside the current activation frame
* heap pointer (NP): points to the next free heap cell for dynamic allocation

Typical responsibilities:

* function prologue sets up a new frame and reserves space for locals/temporaries
* function epilogue restores stack/frame pointers and returns values
* heap allocation advances NP by the required size, failing with a runtime trap if exhausted

### 5.3 Calling convention (high level)

A consistent calling convention is used to:

* pass arguments (typically on stack or via known memory layout)
* allocate a frame for locals
* return values (primitives and composite values)

Composite returns (arrays/records) can be implemented by:

* returning a pointer/address to a heap-allocated value, or
* using a caller-allocated buffer passed implicitly/explicitly, depending on the chosen design

### 5.4 Runtime checks and traps

To remain safe and deterministic, the compiler inserts checks such as:

* array bounds checks
* null/dangling pointer checks (when representable)
* division by zero checks (if applicable)
* out-of-memory checks on allocation

On violation, the runtime triggers a trap or a controlled error path.

## 6. Modules, imports, and exports

DIAL includes a module system in which:

* modules define a namespace boundary
* imports bring external definitions into scope
* exports define the public interface of a module

Semantically, this requires:

* separate symbol tables per module
* clear visibility rules
* stable naming / qualification rules used by both the binder and the code generator

At the backend level, modules can map to:

* a compilation unit strategy (single output WAT with internal namespaces), or
* a multi-unit strategy (linking multiple modules), depending on the implementation approach used in the project.

## 7. Example suite as regression tests

The `ejemplos/` folder is used as a high-signal suite that exercises:

* type-compatibility stress cases
* nested composites (arrays of arrays, arrays/records composition)
* partial access patterns (multi-level designators)
* constant arrays/records, including returning them from functions
* modules/import/export interactions
* switch/case-style constructs
* ternary operator typing
* dynamic memory (new/del and pointer dereference)
* function calls used in designator-like positions (where supported)

This is both documentation of expressiveness and a practical regression baseline for compiler changes.

## 8. How to extend the compiler (developer notes)

Typical extension workflow:

1. Add tokens/lexer rules if new syntax is needed.
2. Extend the CUP grammar and AST node set.
3. Update name binding rules (symbols/scoping).
4. Update type rules and compatibility checks.
5. Update code generation and, if needed, runtime helpers in the prelude/epilogue.
6. Add at least one example under `ejemplos/` that targets the new feature and edge cases.

## 9. References

* DIAL Language Specification: `Especificación_DIAL.pdf`



Si quieres, en el siguiente paso te dejo también un `docs/README.md` mínimo y una sección “Quick demo” de 10 líneas para el README principal (sin comillas), para que cualquiera ejecute un ejemplo representativo del compilador en menos de 1 minuto.

# DIAL Compiler (WebAssembly target)

Compiler for **DIAL**, a custom programming language designed and implemented as a coursework project for **Language Processors** (UCM, 2023–2024).
The compiler generates **WebAssembly Text (WAT)** and can be executed as **WASM** using a small Node.js runtime.

## Authors

* Alejandro Parreño Minaya — GitHub: aparreno14
* Diego Ostos Arellano

## Highlights (why this repo matters)

* Full compiler pipeline: **lexer → parser → AST → name binding → type checking → code generation**.
* Non-trivial language design: modules/import-export, records, pointers + dynamic memory, switch/case, ternary expressions, partial accesses, full compatibility real and integer types (you can always place an integer where a real is expected), etc.
* Backend targets **WebAssembly**, including a small runtime layer (stack/heap, exceptions, I/O).

## Language features (DIAL)

DIAL supports a rich feature set for a student compiler project, including:

* **Modules** with `module`, `import`, `export`
* **Functions** (including calls usable as designators in expressions)
* **Type system**: basic types, arrays (including multi-dimensional), records, enums, aliases, real and integer compatibility
* **Control flow**: if/else, for/while/repeat, break/continue
* **Switch/case** style construct: `case (...) { when ...; default ... }`
* **Ternary operator**: `cond ? a : b`
* **Pointers and dynamic memory**: `T@`, `new`, dereference with `@`, and `del`
* **Composite literals**: array/record literals and returning them from functions

For the full language specification, see: `Especificación_DIAL.pdf` (recommended to keep under `docs/` in this repo).

## Project structure

* `AnalizadorLexico.l` — lexer specification (JFlex)
* `tiny2.cup` — grammar specification (CUP)
* `src/` — Java implementation:

  * `src/alex/` — lexer wrapper and token utilities
  * `src/asint/` — parser entry point
  * `src/ast/` — AST nodes, symbol table stack, type system, and code generation
* `ejemplos/` — example programs used as a functional test suite (switch, modules, pointers, loops, calls, etc.)
* `code/` — WebAssembly runtime artifacts:

  * `preludio.wat` / `epilogo.wat` — runtime helpers (stack/heap management, helpers)
  * `wat2wasm` — converter used to build WASM from WAT
  * `main.js` — Node.js runner (imports for I/O and exception handling)
  * `codigo.wat` / `codigo.wasm` — generated outputs (recommended to be treated as build artifacts)

## How to build the compiler

From repository root:

```
./compilar.sh
```

This script regenerates lexer/parser sources and compiles the Java code.

## How to compile a DIAL program

Compile a DIAL source file (examples available under `ejemplos/`):

```
java -cp "src:src/cup.jar" asint.Main ejemplos/switch/switch1.txt
```

The compiler writes WebAssembly Text output to:

```
code/codigo.wat
```

## How to run the generated program (WASM)

1. Convert WAT to WASM:

   ./code/wat2wasm code/codigo.wat -o code/codigo.wasm

2. Install Node dependency (first time only):

   npm install

3. Run:

   node code/main.js code/codigo.wasm

## Runtime and memory model (WebAssembly)

The runtime layer uses a linear memory with:

* stack pointer (SP) and mark pointer (MP) for stack frames
* heap pointer (NP) for dynamic allocation
* runtime traps for errors such as out-of-bounds array access or out-of-memory

## Examples (recommended)

The `ejemplos/` folder contains a broad set of programs exercising the language:

* modules + import/export
* pointers + records/arrays
* switch/case constructs
* loops and function calls
* ternary operator and type compatibility scenarios

## Examples: advanced scenarios and edge cases

The `ejemplos/` folder is intentionally used as a **high-signal test suite**: beyond basic syntax, it exercises some of the most complex interactions in DIAL’s type system and runtime model.

In particular, you will find examples covering:

* **Type-compatibility stress tests**: non-trivial assignments, coercions, and validity checks across composite and user-defined types.
* **Nested composite types**: *arrays of arrays*, *arrays of records*, and *records containing arrays/records*, including mixed access patterns.
* **Partial accesses**: field/index access on composite values (including multi-level/nested accesses) to validate correct designator semantics.
* **Constant composite literals**: **array and record constants**, including using them directly in expressions and as function return values.
* **Modules**: `module`, `import`, and `export` usage, including cross-module symbol visibility and type usage.
* **Switch/case-style control flow**: `case { when ...; default ... }` patterns, including type-sensitive conditions where applicable.
* **Function calls as designators**: scenarios where function calls are used in designator-like positions (where supported by the language) to validate semantics and code generation.
* **Ternary operator**: `cond ? a : b` with complex operand types and compatibility constraints.
* **Dynamic memory**: pointer types (`T@`), `new`, dereference (`@`), and `del`, including combinations with records/arrays and boundary/error cases.

These examples are meant to be read as both **documentation of language expressiveness** and **regression tests** for the compiler pipeline and the WebAssembly backend.

## License

GPL — see `LICENSE`.

## Disclaimer

Published for educational and portfolio purposes.

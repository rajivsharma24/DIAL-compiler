const { readFileSync } = require("fs");
const readlineSync = require("readline-sync");

const importObjects = {
    runtime: {
        exceptionHandler: function(code) {
            let errStr;
            if (code == 1) {
                errStr = "Error 1. ";
            } else if (code == 2) {
                errStr = "Error, acceso a array fuera de límites ";
            } else if (code == 3) {
                errStr = "Not enough memory. ";
            } else {
                errStr = "Unknown error\n";
            }
            throw new Error(errStr);
        },
        print: function(n) {
            console.log(n);
        },
        readi32: () => {
            const val = readlineSync.question("Enter int: ");
            return parseInt(val);
        },
        readf32: () => {
            const val = readlineSync.question("Enter float: ");
            return parseFloat(val);
        }
    }
};

async function start() {
    var filename = "codigo.wasm";
    if (process.argv.length > 2) {
        filename = process.argv[2];
    }

    const code = readFileSync(filename);
    const wasmModule = await WebAssembly.compile(code);
    const instance = await WebAssembly.instantiate(wasmModule, importObjects);
    process.exit(0);
}

start();
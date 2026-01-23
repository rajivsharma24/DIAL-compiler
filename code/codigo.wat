(module
(type $_sig_i32i32i32 (func (param i32 i32 i32) ))
(type $_sig_i32ri32 (func (param i32) (result i32)))
(type $_sig_i32 (func (param i32)))
(type $_sig_ri32 (func (result i32)))
(type $_sig_f32 (func (param f32)))
(type $_sig_rf32 (func (result f32)))
(type $_sig_void (func ))

(import "runtime" "exceptionHandler" (func $exception (type $_sig_i32)))
(import "runtime" "print" (func $printi32 (type $_sig_i32)))
(import "runtime" "print" (func $printf32 (type $_sig_f32)))
(import "runtime" "readi32" (func $readi32 (type $_sig_ri32)))
(import "runtime" "readf32" (func $readf32 (type $_sig_rf32)))

(memory 2000)
(global $SP (mut i32) (i32.const 0)) ;; start of stack
(global $MP (mut i32) (i32.const 0)) ;; mark pointer
(global $NP (mut i32) (i32.const 131071996)) ;; heap 2000*64*1024-4
(start $preludio)(func $preludio 
 (local $temp i32)
 (local $localsStart i32)
 i32.const 4
 call $reserveStack
 local.set $temp
 global.get $MP
 local.get $temp
 i32.store
 global.get $MP
 i32.const 4
 i32.add
 local.set $localsStart
 call $main
 drop
 call $freeStack
)
 ;;generating code for DECFunc(NONE,separador,[],[PRINT(Int(1111111111))])
(func $separador

 (local $temp i32)
 (local $localsStart i32)
 i32.const 4
 call $reserveStack
 local.set $temp
 global.get $MP
 local.get $temp
 i32.store
 global.get $MP
 i32.const 4
 i32.add
 local.set $localsStart
 i32.const 1111111111
call $printi32

 call $freeStack
)
 ;;end generating code for DECFunc(NONE,separador,[],[PRINT(Int(1111111111))])
 ;;generating code for DECFunc(NONE,mostrarArray,[INT[Int(3)]*REF*arr],[FOR(DEC(INT,i,Int(0)),MENOR(i,Int(3)),SASIG(i,Int(1)),[PRINT(arr[i])])])
(func $mostrarArray

 (local $temp i32)
 (local $localsStart i32)
 i32.const 20
 call $reserveStack
 local.set $temp
 global.get $MP
 local.get $temp
 i32.store
 global.get $MP
 i32.const 4
 i32.add
 local.set $localsStart
 ;;generating code for declaration DEC(INT,i,Int(0))
 i32.const 4
 local.get $localsStart
 i32.add
 i32.const 0
 i32.store
 ;;end generating code for declaration DEC(INT,i,Int(0))
 block
 loop
 i32.const 4
 local.get $localsStart
 i32.add
 i32.load
 i32.const 3
 i32.lt_s
 i32.eqz
 br_if 1
 i32.const 4
 local.get $localsStart
 i32.add
 i32.load
 i32.const 0
 i32.lt_s
 if
 i32.const 2
 call $exception
 end
 i32.const 4
 local.get $localsStart
 i32.add
 i32.load
 i32.const 2
 i32.gt_s
 if
 i32.const 2
 call $exception
 end
 i32.const 0
 local.get $localsStart
 i32.add
 i32.load
 i32.const 4
 local.get $localsStart
 i32.add
 i32.load
 i32.const 4
 i32.mul
 i32.add
 i32.load
call $printi32
 i32.const 4
 local.get $localsStart
 i32.add
 i32.const 4
 local.get $localsStart
 i32.add
 i32.load
 i32.const 1
 i32.add
 i32.store
 br 0
 end
 end

 call $freeStack
)
 ;;end generating code for DECFunc(NONE,mostrarArray,[INT[Int(3)]*REF*arr],[FOR(DEC(INT,i,Int(0)),MENOR(i,Int(3)),SASIG(i,Int(1)),[PRINT(arr[i])])])
 ;;generating code for DECFunc(INT[Int(3)],sumarArray,[INT[Int(3)] arr1, INT[Int(3)] arr2],[DEC(INT[Int(3)],res), FOR(DEC(INT,i,Int(0)),MENOR(i,Int(3)),SASIG(i,Int(1)),[ASIG(res[i],MAS(arr1[i],arr2[i]))]), RETURN(res)])
(func $sumarArray
 (result i32)
 (local $temp i32)
 (local $localsStart i32)
 i32.const 48
 call $reserveStack
 local.set $temp
 global.get $MP
 local.get $temp
 i32.store
 global.get $MP
 i32.const 4
 i32.add
 local.set $localsStart
 ;;generating code for declaration DEC(INT[Int(3)],res)
 ;;end generating code for declaration DEC(INT[Int(3)],res)

 ;;generating code for declaration DEC(INT,i,Int(0))
 i32.const 36
 local.get $localsStart
 i32.add
 i32.const 0
 i32.store
 ;;end generating code for declaration DEC(INT,i,Int(0))
 block
 loop
 i32.const 36
 local.get $localsStart
 i32.add
 i32.load
 i32.const 3
 i32.lt_s
 i32.eqz
 br_if 1
 i32.const 36
 local.get $localsStart
 i32.add
 i32.load
 i32.const 0
 i32.lt_s
 if
 i32.const 2
 call $exception
 end
 i32.const 36
 local.get $localsStart
 i32.add
 i32.load
 i32.const 2
 i32.gt_s
 if
 i32.const 2
 call $exception
 end
 i32.const 24
 local.get $localsStart
 i32.add
 i32.const 36
 local.get $localsStart
 i32.add
 i32.load
 i32.const 4
 i32.mul
 i32.add
 i32.const 36
 local.get $localsStart
 i32.add
 i32.load
 i32.const 0
 i32.lt_s
 if
 i32.const 2
 call $exception
 end
 i32.const 36
 local.get $localsStart
 i32.add
 i32.load
 i32.const 2
 i32.gt_s
 if
 i32.const 2
 call $exception
 end
 i32.const 0
 local.get $localsStart
 i32.add
 i32.const 36
 local.get $localsStart
 i32.add
 i32.load
 i32.const 4
 i32.mul
 i32.add
 i32.load
 i32.const 36
 local.get $localsStart
 i32.add
 i32.load
 i32.const 0
 i32.lt_s
 if
 i32.const 2
 call $exception
 end
 i32.const 36
 local.get $localsStart
 i32.add
 i32.load
 i32.const 2
 i32.gt_s
 if
 i32.const 2
 call $exception
 end
 i32.const 12
 local.get $localsStart
 i32.add
 i32.const 36
 local.get $localsStart
 i32.add
 i32.load
 i32.const 4
 i32.mul
 i32.add
 i32.load
 i32.add
 i32.store
 i32.const 36
 local.get $localsStart
 i32.add
 i32.const 36
 local.get $localsStart
 i32.add
 i32.load
 i32.const 1
 i32.add
 i32.store
 br 0
 end
 end

 global.get $SP
 i32.const 4
 i32.sub
 call $dup_top
 i32.const 24
 local.get $localsStart
 i32.add
 i32.store
 i32.const 24
 local.get $localsStart
 i32.add
 global.get $SP
 i32.const 4
 i32.sub
 call $freeStack
 return
)
 ;;end generating code for DECFunc(INT[Int(3)],sumarArray,[INT[Int(3)] arr1, INT[Int(3)] arr2],[DEC(INT[Int(3)],res), FOR(DEC(INT,i,Int(0)),MENOR(i,Int(3)),SASIG(i,Int(1)),[ASIG(res[i],MAS(arr1[i],arr2[i]))]), RETURN(res)])
 ;;generating code for DECFunc(NONE,sumarArrayRef,[INT[Int(3)]*REF*arr1, INT[Int(3)]*REF*arr2],[FOR(DEC(INT,i,Int(0)),MENOR(i,Int(3)),SASIG(i,Int(1)),[SASIG(arr1[i],arr2[i])])])
(func $sumarArrayRef

 (local $temp i32)
 (local $localsStart i32)
 i32.const 32
 call $reserveStack
 local.set $temp
 global.get $MP
 local.get $temp
 i32.store
 global.get $MP
 i32.const 4
 i32.add
 local.set $localsStart
 ;;generating code for declaration DEC(INT,i,Int(0))
 i32.const 8
 local.get $localsStart
 i32.add
 i32.const 0
 i32.store
 ;;end generating code for declaration DEC(INT,i,Int(0))
 block
 loop
 i32.const 8
 local.get $localsStart
 i32.add
 i32.load
 i32.const 3
 i32.lt_s
 i32.eqz
 br_if 1
 i32.const 8
 local.get $localsStart
 i32.add
 i32.load
 i32.const 0
 i32.lt_s
 if
 i32.const 2
 call $exception
 end
 i32.const 8
 local.get $localsStart
 i32.add
 i32.load
 i32.const 2
 i32.gt_s
 if
 i32.const 2
 call $exception
 end
 i32.const 0
 local.get $localsStart
 i32.add
 i32.load
 i32.const 8
 local.get $localsStart
 i32.add
 i32.load
 i32.const 4
 i32.mul
 i32.add
 i32.const 8
 local.get $localsStart
 i32.add
 i32.load
 i32.const 0
 i32.lt_s
 if
 i32.const 2
 call $exception
 end
 i32.const 8
 local.get $localsStart
 i32.add
 i32.load
 i32.const 2
 i32.gt_s
 if
 i32.const 2
 call $exception
 end
 i32.const 0
 local.get $localsStart
 i32.add
 i32.load
 i32.const 8
 local.get $localsStart
 i32.add
 i32.load
 i32.const 4
 i32.mul
 i32.add
 i32.load
 i32.const 8
 local.get $localsStart
 i32.add
 i32.load
 i32.const 0
 i32.lt_s
 if
 i32.const 2
 call $exception
 end
 i32.const 8
 local.get $localsStart
 i32.add
 i32.load
 i32.const 2
 i32.gt_s
 if
 i32.const 2
 call $exception
 end
 i32.const 4
 local.get $localsStart
 i32.add
 i32.load
 i32.const 8
 local.get $localsStart
 i32.add
 i32.load
 i32.const 4
 i32.mul
 i32.add
 i32.load
 i32.add
 i32.store
 i32.const 8
 local.get $localsStart
 i32.add
 i32.const 8
 local.get $localsStart
 i32.add
 i32.load
 i32.const 1
 i32.add
 i32.store
 br 0
 end
 end

 call $freeStack
)
 ;;end generating code for DECFunc(NONE,sumarArrayRef,[INT[Int(3)]*REF*arr1, INT[Int(3)]*REF*arr2],[FOR(DEC(INT,i,Int(0)),MENOR(i,Int(3)),SASIG(i,Int(1)),[SASIG(arr1[i],arr2[i])])])
 ;;generating code for DECMain([DEC(INT[Int(3)],arr1,[Int(1), Int(2), Int(3)]), DEC(INT[Int(3)],arr2,arr1), DEC(INT[Int(3)],res,sumarArray(arr1,arr2)), ICall(mostrarArray(res)), ICall(separador()), ICall(sumarArrayRef(arr1,arr2)), ICall(mostrarArray(arr1)), RETURN(Int(0))])
(func $main
 (result i32)
 (local $temp i32)
 (local $localsStart i32)
 i32.const 44
 call $reserveStack
 local.set $temp
 global.get $MP
 local.get $temp
 i32.store
 global.get $MP
 i32.const 4
 i32.add
 local.set $localsStart
 ;;generating code for declaration DEC(INT[Int(3)],arr1,[Int(1), Int(2), Int(3)])
 i32.const 0
 local.get $localsStart
 i32.add
 call $dup_top
 ;;generating code for earray[Int(1), Int(2), Int(3)]
 call $dup_top
 i32.const 0
 i32.add
 i32.const 1
 i32.store
 call $dup_top
 i32.const 4
 i32.add
 i32.const 2
 i32.store
 call $dup_top
 i32.const 8
 i32.add
 i32.const 3
 i32.store
 drop
 ;;end generating code for earray[Int(1), Int(2), Int(3)]
 drop
 ;;end generating code for declaration DEC(INT[Int(3)],arr1,[Int(1), Int(2), Int(3)])

 ;;generating code for declaration DEC(INT[Int(3)],arr2,arr1)
 i32.const 12
 local.get $localsStart
 i32.add
 call $dup_top
 i32.const 0
 i32.add
 i32.const 0
 local.get $localsStart
 i32.add
 i32.const 0
 i32.add
 i32.load
 i32.store
 call $dup_top
 i32.const 4
 i32.add
 i32.const 0
 local.get $localsStart
 i32.add
 i32.const 4
 i32.add
 i32.load
 i32.store
 call $dup_top
 i32.const 8
 i32.add
 i32.const 0
 local.get $localsStart
 i32.add
 i32.const 8
 i32.add
 i32.load
 i32.store
 drop
 ;;end generating code for declaration DEC(INT[Int(3)],arr2,arr1)

 ;;generating code for declaration DEC(INT[Int(3)],res,sumarArray(arr1,arr2))
 i32.const 24
 local.get $localsStart
 i32.add
 call $dup_top
 i32.const 0
 i32.add
 global.get $SP
 i32.const 4
 i32.add
 i32.const 0
 i32.add
 call $dup_top
 i32.const 0
 i32.add
 i32.const 0
 local.get $localsStart
 i32.add
 i32.const 0
 i32.add
 i32.load
 i32.store
 call $dup_top
 i32.const 4
 i32.add
 i32.const 0
 local.get $localsStart
 i32.add
 i32.const 4
 i32.add
 i32.load
 i32.store
 call $dup_top
 i32.const 8
 i32.add
 i32.const 0
 local.get $localsStart
 i32.add
 i32.const 8
 i32.add
 i32.load
 i32.store
 drop
 global.get $SP
 i32.const 4
 i32.add
 i32.const 12
 i32.add
 call $dup_top
 i32.const 0
 i32.add
 i32.const 12
 local.get $localsStart
 i32.add
 i32.const 0
 i32.add
 i32.load
 i32.store
 call $dup_top
 i32.const 4
 i32.add
 i32.const 12
 local.get $localsStart
 i32.add
 i32.const 4
 i32.add
 i32.load
 i32.store
 call $dup_top
 i32.const 8
 i32.add
 i32.const 12
 local.get $localsStart
 i32.add
 i32.const 8
 i32.add
 i32.load
 i32.store
 drop
 call $sumarArray
 i32.load
 i32.const 0
 i32.add
 i32.load
 i32.store
 call $dup_top
 i32.const 4
 i32.add
 global.get $SP
 i32.const 4
 i32.add
 i32.const 0
 i32.add
 call $dup_top
 i32.const 0
 i32.add
 i32.const 0
 local.get $localsStart
 i32.add
 i32.const 0
 i32.add
 i32.load
 i32.store
 call $dup_top
 i32.const 4
 i32.add
 i32.const 0
 local.get $localsStart
 i32.add
 i32.const 4
 i32.add
 i32.load
 i32.store
 call $dup_top
 i32.const 8
 i32.add
 i32.const 0
 local.get $localsStart
 i32.add
 i32.const 8
 i32.add
 i32.load
 i32.store
 drop
 global.get $SP
 i32.const 4
 i32.add
 i32.const 12
 i32.add
 call $dup_top
 i32.const 0
 i32.add
 i32.const 12
 local.get $localsStart
 i32.add
 i32.const 0
 i32.add
 i32.load
 i32.store
 call $dup_top
 i32.const 4
 i32.add
 i32.const 12
 local.get $localsStart
 i32.add
 i32.const 4
 i32.add
 i32.load
 i32.store
 call $dup_top
 i32.const 8
 i32.add
 i32.const 12
 local.get $localsStart
 i32.add
 i32.const 8
 i32.add
 i32.load
 i32.store
 drop
 call $sumarArray
 i32.load
 i32.const 4
 i32.add
 i32.load
 i32.store
 call $dup_top
 i32.const 8
 i32.add
 global.get $SP
 i32.const 4
 i32.add
 i32.const 0
 i32.add
 call $dup_top
 i32.const 0
 i32.add
 i32.const 0
 local.get $localsStart
 i32.add
 i32.const 0
 i32.add
 i32.load
 i32.store
 call $dup_top
 i32.const 4
 i32.add
 i32.const 0
 local.get $localsStart
 i32.add
 i32.const 4
 i32.add
 i32.load
 i32.store
 call $dup_top
 i32.const 8
 i32.add
 i32.const 0
 local.get $localsStart
 i32.add
 i32.const 8
 i32.add
 i32.load
 i32.store
 drop
 global.get $SP
 i32.const 4
 i32.add
 i32.const 12
 i32.add
 call $dup_top
 i32.const 0
 i32.add
 i32.const 12
 local.get $localsStart
 i32.add
 i32.const 0
 i32.add
 i32.load
 i32.store
 call $dup_top
 i32.const 4
 i32.add
 i32.const 12
 local.get $localsStart
 i32.add
 i32.const 4
 i32.add
 i32.load
 i32.store
 call $dup_top
 i32.const 8
 i32.add
 i32.const 12
 local.get $localsStart
 i32.add
 i32.const 8
 i32.add
 i32.load
 i32.store
 drop
 call $sumarArray
 i32.load
 i32.const 8
 i32.add
 i32.load
 i32.store
 drop
 ;;end generating code for declaration DEC(INT[Int(3)],res,sumarArray(arr1,arr2))

 global.get $SP
 i32.const 4
 i32.add
 i32.const 0
 i32.add
 i32.const 24
 local.get $localsStart
 i32.add
 i32.store
 call $mostrarArray

 call $separador

 global.get $SP
 i32.const 4
 i32.add
 i32.const 0
 i32.add
 i32.const 0
 local.get $localsStart
 i32.add
 i32.store
 global.get $SP
 i32.const 4
 i32.add
 i32.const 4
 i32.add
 i32.const 12
 local.get $localsStart
 i32.add
 i32.store
 call $sumarArrayRef

 global.get $SP
 i32.const 4
 i32.add
 i32.const 0
 i32.add
 i32.const 0
 local.get $localsStart
 i32.add
 i32.store
 call $mostrarArray

 global.get $SP
 i32.const 4
 i32.sub
 i32.const 0
i32.store ;;hola
 global.get $SP
 i32.const 4
 i32.sub
 call $freeStack
 return
)
 ;;end generating code for DECMain([DEC(INT[Int(3)],arr1,[Int(1), Int(2), Int(3)]), DEC(INT[Int(3)],arr2,arr1), DEC(INT[Int(3)],res,sumarArray(arr1,arr2)), ICall(mostrarArray(res)), ICall(separador()), ICall(sumarArrayRef(arr1,arr2)), ICall(mostrarArray(arr1)), RETURN(Int(0))])
(func $reserveStack (param $size i32)
   (result i32)
   global.get $MP
   global.get $SP
   global.set $MP
   global.get $SP
   local.get $size
   i32.add
   global.set $SP
   global.get $SP
   global.get $NP
   i32.gt_u
   if
   i32.const 3
   call $exception
   end
)

(func $freeStack (type $_sig_void)
   global.get $MP
   global.set $SP
   global.get $MP
   i32.load
   global.set $MP   
)

(func $reserveHeap (type $_sig_i32)
   (param $size i32)


   global.get $NP
   local.get $size
   i32.sub
   global.set $NP

   global.get $SP
   global.get $NP 
   i32.gt_u ;; comprobamos si SP es mayor que NP
   if
   i32.const 3
   call $exception ;; se han cruzado, error
   end
)

;; Función swap_i32
  (func $swap_i32 (param $a i32) (param $b i32) (result i32 i32)
    local.get $b
    local.get $a
  )

  ;; Función dup_top
  (func $dup_top (param $temp i32) (result i32 i32)
    local.get $temp      ;; Empuja x [x]
    local.get $temp      ;; Empuja x otra vez [x, x]
  )
  
  (func $swap_i32f32 (param $a i32) (param $b f32) (result f32 i32)
    local.get $b
    local.get $a
  )
  
  (func $swap_f32 (param $a f32) (param $b f32) (result f32 f32)
    local.get $b
    local.get $a
  )


(func $copyn (type $_sig_i32i32i32) ;; copy $n i32 slots from $src to $dest
   (param $src i32)
   (param $dest i32)
   (param $n i32)
   block
     loop
       local.get $n
       i32.eqz
       br_if 1
       local.get $n
       i32.const 1
       i32.sub
       local.set $n
       local.get $dest
       local.get $src
       i32.load
       i32.store
       local.get $dest
       i32.const 4
       i32.add
       local.set $dest
       local.get $src
       i32.const 4
       i32.add
       local.set $src
       br 0
     end
   end
)
(export "memory" (memory 0))
(export "init" (func $preludio))
)
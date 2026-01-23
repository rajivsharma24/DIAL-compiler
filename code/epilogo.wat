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
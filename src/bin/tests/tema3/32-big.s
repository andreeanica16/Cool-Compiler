.data
    .align  2
    .globl  class_nameTab
    .globl  Int_protObj
    .globl  String_protObj
    .globl  bool_const0
    .globl  bool_const1
    .globl  Main_protObj
    .globl  _int_tag
    .globl  _string_tag
    .globl  _bool_tag

_int_tag:
    .word   1
_string_tag:
    .word   3
_bool_tag:
    .word   2

bool_const0:
    .word   2
    .word   4
    .word   Bool_dispTab
    .word   0
bool_const1:
    .word 2
    .word   4
    .word   Bool_dispTab
    .word   1

int_const0:
    .word   1
    .word   4
    .word   Int_dispTab
    .word   0
str_const0:
    .word   3
    .word   5
    .word   String_dispTab
    .word   int_const0
    .asciiz ""
    .align  2
int_const6:
    .word   1
    .word   4
    .word   Int_dispTab
    .word   6
str_const1:
    .word   3
    .word   6
    .word   String_dispTab
    .word   int_const6
    .asciiz "Object"
    .align  2
int_const3:
    .word   1
    .word   4
    .word   Int_dispTab
    .word   3
str_const2:
    .word   3
    .word   6
    .word   String_dispTab
    .word   int_const3
    .asciiz "Int"
    .align  2
int_const4:
    .word   1
    .word   4
    .word   Int_dispTab
    .word   4
str_const3:
    .word   3
    .word   6
    .word   String_dispTab
    .word   int_const4
    .asciiz "Bool"
    .align  2
str_const4:
    .word   3
    .word   6
    .word   String_dispTab
    .word   int_const6
    .asciiz "String"
    .align  2
int_const2:
    .word   1
    .word   4
    .word   Int_dispTab
    .word   2
str_const5:
    .word   3
    .word   5
    .word   String_dispTab
    .word   int_const2
    .asciiz "IO"
    .align  2
str_const6:
    .word   3
    .word   6
    .word   String_dispTab
    .word   int_const4
    .asciiz "List"
    .align  2
str_const7:
    .word   3
    .word   6
    .word   String_dispTab
    .word   int_const4
    .asciiz "Main"
    .align  2
str_const8:
    .word   3
    .word   6
    .word   String_dispTab
    .word   int_const3
    .asciiz "A2I"
    .align  2
int_const9:
    .word   1
    .word   4
    .word   Int_dispTab
    .word   9
str_const9:
    .word   3
    .word   7
    .word   String_dispTab
    .word   int_const9
    .asciiz "32-big.cl"
    .align  2
int_const1:
    .word   1
    .word   4
    .word   Int_dispTab
    .word   1
str_const10:
    .word   3
    .word   5
    .word   String_dispTab
    .word   int_const1
    .asciiz " "
    .align  2
str_const11:
    .word   3
    .word   5
    .word   String_dispTab
    .word   int_const1
    .asciiz "
"
    .align  2
str_const12:
    .word   3
    .word   5
    .word   String_dispTab
    .word   int_const1
    .asciiz "!"
    .align  2
int_const27:
    .word   1
    .word   4
    .word   Int_dispTab
    .word   27
str_const13:
    .word   3
    .word   12
    .word   String_dispTab
    .word   int_const27
    .asciiz "Calculam factorial pentru: "
    .align  2
int_const20:
    .word   1
    .word   4
    .word   Int_dispTab
    .word   20
str_const14:
    .word   3
    .word   10
    .word   String_dispTab
    .word   int_const20
    .asciiz "Factorial recursiv: "
    .align  2
str_const15:
    .word   3
    .word   10
    .word   String_dispTab
    .word   int_const20
    .asciiz "Factorial iterativ: "
    .align  2
int_const10:
    .word   1
    .word   4
    .word   Int_dispTab
    .word   10
str_const16:
    .word   3
    .word   5
    .word   String_dispTab
    .word   int_const1
    .asciiz "0"
    .align  2
str_const17:
    .word   3
    .word   5
    .word   String_dispTab
    .word   int_const1
    .asciiz "-"
    .align  2
str_const18:
    .word   3
    .word   5
    .word   String_dispTab
    .word   int_const1
    .asciiz "+"
    .align  2
str_const19:
    .word   3
    .word   5
    .word   String_dispTab
    .word   int_const1
    .asciiz "1"
    .align  2
str_const20:
    .word   3
    .word   5
    .word   String_dispTab
    .word   int_const1
    .asciiz "2"
    .align  2
str_const21:
    .word   3
    .word   5
    .word   String_dispTab
    .word   int_const1
    .asciiz "3"
    .align  2
str_const22:
    .word   3
    .word   5
    .word   String_dispTab
    .word   int_const1
    .asciiz "4"
    .align  2
int_const5:
    .word   1
    .word   4
    .word   Int_dispTab
    .word   5
str_const23:
    .word   3
    .word   5
    .word   String_dispTab
    .word   int_const1
    .asciiz "5"
    .align  2
str_const24:
    .word   3
    .word   5
    .word   String_dispTab
    .word   int_const1
    .asciiz "6"
    .align  2
int_const7:
    .word   1
    .word   4
    .word   Int_dispTab
    .word   7
str_const25:
    .word   3
    .word   5
    .word   String_dispTab
    .word   int_const1
    .asciiz "7"
    .align  2
int_const8:
    .word   1
    .word   4
    .word   Int_dispTab
    .word   8
str_const26:
    .word   3
    .word   5
    .word   String_dispTab
    .word   int_const1
    .asciiz "8"
    .align  2
str_const27:
    .word   3
    .word   5
    .word   String_dispTab
    .word   int_const1
    .asciiz "9"
    .align  2

class_nameTab:
	.word	str_const1
	.word	str_const2
	.word	str_const3
	.word	str_const4
	.word	str_const5
	.word	str_const6
	.word	str_const7
	.word	str_const8

class_objTab:
    .word   Object_protObj
    .word   Object_init
    .word   Int_protObj
    .word   Int_init
    .word   Bool_protObj
    .word   Bool_init
    .word   String_protObj
    .word   String_init
    .word   IO_protObj
    .word   IO_init
    .word   List_protObj
    .word   List_init
    .word   Main_protObj
    .word   Main_init
    .word   A2I_protObj
    .word   A2I_init

Object_protObj:
    .word   0
    .word   3
    .word   Object_dispTab

Int_protObj:
    .word   1
    .word   4
    .word   Int_dispTab
	.word	0
Bool_protObj:
    .word   2
    .word   4
    .word   Bool_dispTab
	.word	0
String_protObj:
    .word   3
    .word   5
    .word   String_dispTab
	.word	int_const0
	.asciiz	""
	.align	2
IO_protObj:
    .word   4
    .word   3
    .word   IO_dispTab

List_protObj:
    .word   5
    .word   3
    .word   List_dispTab
	.word	0
	.word	0

Main_protObj:
    .word   6
    .word   3
    .word   Main_dispTab

A2I_protObj:
    .word   7
    .word   3
    .word   A2I_dispTab


Object_dispTab:
    .word   Object.abort
    .word   Object.copy
    .word   Object.type_name

Int_dispTab:
    .word   Object.abort
    .word   Object.copy
    .word   Object.type_name

Bool_dispTab:
    .word   Object.abort
    .word   Object.copy
    .word   Object.type_name

String_dispTab:
    .word   Object.abort
    .word   Object.copy
    .word   Object.type_name
    .word   String.length
    .word   String.concat
    .word   String.substr

IO_dispTab:
    .word   Object.abort
    .word   Object.copy
    .word   Object.type_name
    .word   IO.in_string
    .word   IO.in_int
    .word   IO.out_string
    .word   IO.out_int

List_dispTab:
    .word   Object.abort
    .word   Object.copy
    .word   Object.type_name
    .word   IO.in_string
    .word   IO.in_int
    .word   IO.out_string
    .word   IO.out_int
    .word   List.init
    .word   List.print

Main_dispTab:
    .word   Object.abort
    .word   Object.copy
    .word   Object.type_name
    .word   IO.in_string
    .word   IO.in_int
    .word   IO.out_string
    .word   IO.out_int
    .word   Main.main
    .word   Main.fact_rec
    .word   Main.fact_iter

A2I_dispTab:
    .word   Object.abort
    .word   Object.copy
    .word   Object.type_name
    .word   A2I.c2i
    .word   A2I.i2c
    .word   A2I.a2i
    .word   A2I.a2i_aux
    .word   A2I.i2a
    .word   A2I.i2a_aux


    .globl  heap_start
heap_start:
    .word   0
    .text
    .globl Int_init
    .globl String_init
    .globl Bool_init
    .globl Main_init
    .globl Main.main
Object_init:
    addiu   $sp $sp -12
    sw  $fp 12($sp)
    sw  $s0 8($sp)
    sw  $ra 4($sp)
    addiu   $fp $sp 4
    move    $s0 $a0
    move    $a0 $s0
    lw  $fp 12($sp)
    lw  $s0 8($sp)
    lw  $ra 4($sp)
    addiu   $sp $sp 12
    jr  $ra


Bool_init:
    addiu   $sp $sp -12
    sw  $fp 12($sp)
    sw  $s0 8($sp)
    sw  $ra 4($sp)
    addiu   $fp $sp 4
    move    $s0 $a0
    jal Object_init
    move    $a0 $s0
    lw  $fp 12($sp)
    lw  $s0 8($sp)
    lw  $ra 4($sp)
    addiu   $sp $sp 12
    jr  $ra

String_init:
    addiu   $sp $sp -12
    sw  $fp 12($sp)
    sw  $s0 8($sp)
    sw  $ra 4($sp)
    addiu   $fp $sp 4
    move    $s0 $a0
    jal Object_init
    move    $a0 $s0
    lw  $fp 12($sp)
    lw  $s0 8($sp)
    lw  $ra 4($sp)
    addiu   $sp $sp 12
    jr  $ra

Int_init:
    addiu   $sp $sp -12
    sw  $fp 12($sp)
    sw  $s0 8($sp)
    sw  $ra 4($sp)
    addiu   $fp $sp 4
    move    $s0 $a0
    jal Object_init
    move    $a0 $s0
    lw  $fp 12($sp)
    lw  $s0 8($sp)
    lw  $ra 4($sp)
    addiu   $sp $sp 12
    jr  $ra

IO_init:
    addiu   $sp $sp -12
    sw  $fp 12($sp)
    sw  $s0 8($sp)
    sw  $ra 4($sp)
    addiu   $fp $sp 4
    move    $s0 $a0
    jal Object_init
    move    $a0 $s0
    lw  $fp 12($sp)
    lw  $s0 8($sp)
    lw  $ra 4($sp)
    addiu   $sp $sp 12
    jr  $ra

List_init:
    addiu   $sp $sp -12
    sw  $fp 12($sp)
    sw  $s0 8($sp)
    sw  $ra 4($sp)
    addiu   $fp $sp 4
    move    $s0 $a0
    jal IO_init
    move    $a0 $s0
    lw  $fp 12($sp)
    lw  $s0 8($sp)
    lw  $ra 4($sp)
    addiu   $sp $sp 12
    jr  $ra

List.print:
    addiu   $sp $sp -12
    sw  $fp 12($sp)
    sw  $s0 8($sp)
    sw  $ra 4($sp)
    addiu   $fp $sp 4
    move    $s0 $a0
	addiu	$sp $sp -4
    addiu   $sp $sp -4   # locals alloc
    move    $s0 $a0
    lw  $a0 16($s0)
    bnez    $a0 case0
    la      $a0 str_const9
    li      $t1 24
    jal     _case_abort2
case0:
    sw      $a0 -4($fp)
    lw      $t1 0($a0)      # class tag
	blt		$t1 3 casebranch1
	bgt     $t1 3 casebranch1
    lw  $a0 -4($fp)
	b		endcase0
casebranch1:
	blt		$t1 1 casebranch2
	bgt     $t1 1 casebranch2
    lw  $a0 -4($fp)
	sw      $a0 0($sp)
    addiu   $sp $sp -4

    la  $a0 A2I_protObj
	jal Object.copy
	jal A2I_init
	bnez    $a0 dispatch_0
	la      $a0 str_const9
	li      $t1 26
	jal     _dispatch_abort
dispatch_0:
	lw      $t1 8($a0)
    lw      $t1 28($t1)
    jalr    $t1
	b		endcase0
casebranch2:
	blt		$t1 0 casebranch3
	bgt     $t1 7 casebranch3
	move    $a0 $s0
	bnez    $a0 dispatch_1
	la      $a0 str_const9
	li      $t1 27
	jal     _dispatch_abort
dispatch_1:
	lw      $t1 8($a0)
    lw      $t1 0($t1)
    jalr    $t1
    la  $a0 str_const0
	b		endcase0
casebranch3:

	lw		$a0 -4($fp)
	jal		_case_abort
endcase0:
    addiu   $sp $sp 4    # locals free

    sw  $a0 -4($fp)

    la  $a0 str_const10
	sw      $a0 0($sp)
    addiu   $sp $sp -4

    lw  $a0 -4($fp)
	bnez    $a0 dispatch_3
	la      $a0 str_const9
	li      $t1 31
	jal     _dispatch_abort
dispatch_3:
	lw      $t1 8($a0)
    lw      $t1 16($t1)
    jalr    $t1
	sw      $a0 0($sp)
    addiu   $sp $sp -4

	move    $a0 $s0
	bnez    $a0 dispatch_2
	la      $a0 str_const9
	li      $t1 31
	jal     _dispatch_abort
dispatch_2:
	lw      $t1 8($a0)
    lw      $t1 20($t1)
    jalr    $t1
    lw  $a0 12($s0)
    move    $t1 $a0
    la      $a0 bool_const1
    beqz    $t1 isvoid0
    la      $a0 bool_const0
isvoid0:
	lw      $t1 12($a0)     # bool slot
	beqz    $t1 else_0
    la  $a0 str_const11
	sw      $a0 0($sp)
    addiu   $sp $sp -4

	move    $a0 $s0
	bnez    $a0 dispatch_4
	la      $a0 str_const9
	li      $t1 32
	jal     _dispatch_abort
dispatch_4:
	lw      $t1 8($a0)
    lw      $t1 20($t1)
    jalr    $t1
	b   endif0
else_0:
    lw  $a0 12($s0)
	bnez    $a0 dispatch_5
	la      $a0 str_const9
	li      $t1 32
	jal     _dispatch_abort
dispatch_5:
	lw      $t1 8($a0)
    lw      $t1 32($t1)
    jalr    $t1
endif0:
	addiu	$sp $sp 4

    lw  $fp 12($sp)
    lw  $s0 8($sp)
    lw  $ra 4($sp)
    addiu   $sp $sp 12
    jr  $ra

List.init:
    addiu   $sp $sp -12
    sw  $fp 12($sp)
    sw  $s0 8($sp)
    sw  $ra 4($sp)
    addiu   $fp $sp 4
    move    $s0 $a0
    lw  $a0 12($fp)
    sw  $a0 16($s0)

    lw  $a0 16($fp)
    sw  $a0 12($s0)
	move	$a0 $s0
    lw  $fp 12($sp)
    lw  $s0 8($sp)
    lw  $ra 4($sp)
    addiu   $sp $sp 20
    jr  $ra

Main_init:
    addiu   $sp $sp -12
    sw  $fp 12($sp)
    sw  $s0 8($sp)
    sw  $ra 4($sp)
    addiu   $fp $sp 4
    move    $s0 $a0
    jal IO_init
    move    $a0 $s0
    lw  $fp 12($sp)
    lw  $s0 8($sp)
    lw  $ra 4($sp)
    addiu   $sp $sp 12
    jr  $ra

Main.fact_iter:
    addiu   $sp $sp -12
    sw  $fp 12($sp)
    sw  $s0 8($sp)
    sw  $ra 4($sp)
    addiu   $fp $sp 4
    move    $s0 $a0
	addiu	$sp $sp -4
    la  $a0 int_const1
    sw  $a0 -4($fp)

while_0:
    lw  $a0 12($fp)
    sw      $a0 0($sp)
    addiu   $sp $sp -4
    la  $a0 int_const0
    lw      $t1 4($sp)
    addiu   $sp $sp 4
    move    $t2 $a0
    la      $a0 bool_const1
    beq     $t1 $t2 eq0
    la      $a1 bool_const0
    jal     equality_test
eq0:
	lw		$t1 12($a0)
	la		$a0 bool_const1
	beqz	$t1 not0
	la		$a0 bool_const0
not0:
	lw		$t1 12($a0)
	beqz	$t1 end_while_0
    lw  $a0 -4($fp)
	sw		$a0 0($sp)
	addiu	$sp $sp -4
    lw  $a0 12($fp)
	jal		Object.copy
	lw		$t1 4($sp)
	addiu	$sp $sp 4
	lw		$t1 12($t1)
	lw		$t2 12($a0)
	mul	$t1 $t1 $t2
	sw		$t1 12($a0)
    sw  $a0 -4($fp)
    lw  $a0 12($fp)
	sw		$a0 0($sp)
	addiu	$sp $sp -4
    la  $a0 int_const1
	jal		Object.copy
	lw		$t1 4($sp)
	addiu	$sp $sp 4
	lw		$t1 12($t1)
	lw		$t2 12($a0)
	sub	$t1 $t1 $t2
	sw		$t1 12($a0)
    sw  $a0 12($fp)
	b		while_0
end_while_0:
	li		$a0 0

    lw  $a0 -4($fp)
	addiu	$sp $sp 4

    lw  $fp 12($sp)
    lw  $s0 8($sp)
    lw  $ra 4($sp)
    addiu   $sp $sp 16
    jr  $ra

Main.fact_rec:
    addiu   $sp $sp -12
    sw  $fp 12($sp)
    sw  $s0 8($sp)
    sw  $ra 4($sp)
    addiu   $fp $sp 4
    move    $s0 $a0
    lw  $a0 12($fp)
    sw      $a0 0($sp)
    addiu   $sp $sp -4
    la  $a0 int_const0
    lw      $t1 4($sp)
    addiu   $sp $sp 4
    move    $t2 $a0
    la      $a0 bool_const1
    beq     $t1 $t2 eq1
    la      $a1 bool_const0
    jal     equality_test
eq1:
	lw      $t1 12($a0)     # bool slot
	beqz    $t1 else_1
    la  $a0 int_const1
	b   endif1
else_1:
    lw  $a0 12($fp)
	sw		$a0 0($sp)
	addiu	$sp $sp -4
    lw  $a0 12($fp)
	sw		$a0 0($sp)
	addiu	$sp $sp -4
    la  $a0 int_const1
	jal		Object.copy
	lw		$t1 4($sp)
	addiu	$sp $sp 4
	lw		$t1 12($t1)
	lw		$t2 12($a0)
	sub	$t1 $t1 $t2
	sw		$t1 12($a0)
	sw      $a0 0($sp)
    addiu   $sp $sp -4

	move    $a0 $s0
	bnez    $a0 dispatch_6
	la      $a0 str_const9
	li      $t1 65
	jal     _dispatch_abort
dispatch_6:
	lw      $t1 8($a0)
    lw      $t1 32($t1)
    jalr    $t1
	jal		Object.copy
	lw		$t1 4($sp)
	addiu	$sp $sp 4
	lw		$t1 12($t1)
	lw		$t2 12($a0)
	mul	$t1 $t1 $t2
	sw		$t1 12($a0)
endif1:
    lw  $fp 12($sp)
    lw  $s0 8($sp)
    lw  $ra 4($sp)
    addiu   $sp $sp 16
    jr  $ra

Main.main:
    addiu   $sp $sp -12
    sw  $fp 12($sp)
    sw  $s0 8($sp)
    sw  $ra 4($sp)
    addiu   $fp $sp 4
    move    $s0 $a0
	addiu	$sp $sp -20
    la  $a0 int_const0
    sw  $a0 -4($fp)
    la  $a0 str_const12
    sw  $a0 -8($fp)
    lw  $a0 -4($fp)
	sw		$a0 0($sp)
	addiu	$sp $sp -4
    la  $a0 int_const2
	jal		Object.copy
	lw		$t1 4($sp)
	addiu	$sp $sp 4
	lw		$t1 12($t1)
	lw		$t2 12($a0)
	add	$t1 $t1 $t2
	sw		$t1 12($a0)
    sw  $a0 -12($fp)
	li	$a0 0
    sw  $a0 -16($fp)
    lw  $a0 -16($fp)
	sw      $a0 0($sp)
    addiu   $sp $sp -4
    lw  $a0 -12($fp)
	sw      $a0 0($sp)
    addiu   $sp $sp -4

    la  $a0 List_protObj
	jal Object.copy
	jal List_init
	bnez    $a0 dispatch_9
	la      $a0 str_const9
	li      $t1 47
	jal     _dispatch_abort
dispatch_9:
	lw      $t1 8($a0)
    lw      $t1 28($t1)
    jalr    $t1
	sw      $a0 0($sp)
    addiu   $sp $sp -4
    lw  $a0 -8($fp)
	sw      $a0 0($sp)
    addiu   $sp $sp -4

    la  $a0 List_protObj
	jal Object.copy
	jal List_init
	bnez    $a0 dispatch_8
	la      $a0 str_const9
	li      $t1 46
	jal     _dispatch_abort
dispatch_8:
	lw      $t1 8($a0)
    lw      $t1 28($t1)
    jalr    $t1
	sw      $a0 0($sp)
    addiu   $sp $sp -4
    lw  $a0 -4($fp)
	sw      $a0 0($sp)
    addiu   $sp $sp -4

    la  $a0 List_protObj
	jal Object.copy
	jal List_init
	bnez    $a0 dispatch_7
	la      $a0 str_const9
	li      $t1 45
	jal     _dispatch_abort
dispatch_7:
	lw      $t1 8($a0)
    lw      $t1 28($t1)
    jalr    $t1
    sw  $a0 -20($fp)


    lw  $a0 -20($fp)
	bnez    $a0 dispatch_10
	la      $a0 str_const9
	li      $t1 49
	jal     _dispatch_abort
dispatch_10:
	lw      $t1 8($a0)
    lw      $t1 32($t1)
    jalr    $t1
	addiu	$sp $sp 20

	addiu	$sp $sp -4
    la  $a0 str_const13
	sw      $a0 0($sp)
    addiu   $sp $sp -4

	move    $a0 $s0
	bnez    $a0 dispatch_12
	la      $a0 str_const9
	li      $t1 52
	jal     _dispatch_abort
dispatch_12:
	lw      $t1 8($a0)
    lw      $t1 20($t1)
    jalr    $t1
	bnez    $a0 dispatch_11
	la      $a0 str_const9
	li      $t1 52
	jal     _dispatch_abort
dispatch_11:
	lw      $t1 8($a0)
    lw      $t1 16($t1)
    jalr    $t1
    sw  $a0 -4($fp)

    la  $a0 str_const11
	sw      $a0 0($sp)
    addiu   $sp $sp -4

    lw  $a0 -4($fp)
	sw      $a0 0($sp)
    addiu   $sp $sp -4

	move    $a0 $s0
	bnez    $a0 dispatch_15
	la      $a0 str_const9
	li      $t1 55
	jal     _dispatch_abort
dispatch_15:
	lw      $t1 8($a0)
    lw      $t1 32($t1)
    jalr    $t1
	sw      $a0 0($sp)
    addiu   $sp $sp -4

    la  $a0 str_const14
	sw      $a0 0($sp)
    addiu   $sp $sp -4

	move    $a0 $s0
	bnez    $a0 dispatch_16
	la      $a0 str_const9
	li      $t1 55
	jal     _dispatch_abort
dispatch_16:
	lw      $t1 8($a0)
    lw      $t1 20($t1)
    jalr    $t1
	bnez    $a0 dispatch_14
	la      $a0 str_const9
	li      $t1 55
	jal     _dispatch_abort
dispatch_14:
	lw      $t1 8($a0)
    lw      $t1 24($t1)
    jalr    $t1
	bnez    $a0 dispatch_13
	la      $a0 str_const9
	li      $t1 55
	jal     _dispatch_abort
dispatch_13:
	lw      $t1 8($a0)
    lw      $t1 20($t1)
    jalr    $t1
    la  $a0 str_const11
	sw      $a0 0($sp)
    addiu   $sp $sp -4

    lw  $a0 -4($fp)
	sw      $a0 0($sp)
    addiu   $sp $sp -4

	move    $a0 $s0
	bnez    $a0 dispatch_19
	la      $a0 str_const9
	li      $t1 57
	jal     _dispatch_abort
dispatch_19:
	lw      $t1 8($a0)
    lw      $t1 36($t1)
    jalr    $t1
	sw      $a0 0($sp)
    addiu   $sp $sp -4

    la  $a0 str_const15
	sw      $a0 0($sp)
    addiu   $sp $sp -4

	move    $a0 $s0
	bnez    $a0 dispatch_20
	la      $a0 str_const9
	li      $t1 57
	jal     _dispatch_abort
dispatch_20:
	lw      $t1 8($a0)
    lw      $t1 20($t1)
    jalr    $t1
	bnez    $a0 dispatch_18
	la      $a0 str_const9
	li      $t1 57
	jal     _dispatch_abort
dispatch_18:
	lw      $t1 8($a0)
    lw      $t1 24($t1)
    jalr    $t1
	bnez    $a0 dispatch_17
	la      $a0 str_const9
	li      $t1 57
	jal     _dispatch_abort
dispatch_17:
	lw      $t1 8($a0)
    lw      $t1 20($t1)
    jalr    $t1
	addiu	$sp $sp 4

    lw  $fp 12($sp)
    lw  $s0 8($sp)
    lw  $ra 4($sp)
    addiu   $sp $sp 12
    jr  $ra

A2I_init:
    addiu   $sp $sp -12
    sw  $fp 12($sp)
    sw  $s0 8($sp)
    sw  $ra 4($sp)
    addiu   $fp $sp 4
    move    $s0 $a0
    jal Object_init
    move    $a0 $s0
    lw  $fp 12($sp)
    lw  $s0 8($sp)
    lw  $ra 4($sp)
    addiu   $sp $sp 12
    jr  $ra

A2I.i2a_aux:
    addiu   $sp $sp -12
    sw  $fp 12($sp)
    sw  $s0 8($sp)
    sw  $ra 4($sp)
    addiu   $fp $sp 4
    move    $s0 $a0
    lw  $a0 12($fp)
    sw      $a0 0($sp)
    addiu   $sp $sp -4
    la  $a0 int_const0
    lw      $t1 4($sp)
    addiu   $sp $sp 4
    move    $t2 $a0
    la      $a0 bool_const1
    beq     $t1 $t2 eq2
    la      $a1 bool_const0
    jal     equality_test
eq2:
	lw      $t1 12($a0)     # bool slot
	beqz    $t1 else_2
    la  $a0 str_const0
	b   endif2
else_2:
	addiu	$sp $sp -4
    lw  $a0 12($fp)
	sw		$a0 0($sp)
	addiu	$sp $sp -4
    la  $a0 int_const10
	jal		Object.copy
	lw		$t1 4($sp)
	addiu	$sp $sp 4
	lw		$t1 12($t1)
	lw		$t2 12($a0)
	div	$t1 $t1 $t2
	sw		$t1 12($a0)
    sw  $a0 -4($fp)

    lw  $a0 12($fp)
	sw		$a0 0($sp)
	addiu	$sp $sp -4
    lw  $a0 -4($fp)
	sw		$a0 0($sp)
	addiu	$sp $sp -4
    la  $a0 int_const10
	jal		Object.copy
	lw		$t1 4($sp)
	addiu	$sp $sp 4
	lw		$t1 12($t1)
	lw		$t2 12($a0)
	mul	$t1 $t1 $t2
	sw		$t1 12($a0)
	jal		Object.copy
	lw		$t1 4($sp)
	addiu	$sp $sp 4
	lw		$t1 12($t1)
	lw		$t2 12($a0)
	sub	$t1 $t1 $t2
	sw		$t1 12($a0)
	sw      $a0 0($sp)
    addiu   $sp $sp -4

	move    $a0 $s0
	bnez    $a0 dispatch_22
	la      $a0 str_const9
	li      $t1 188
	jal     _dispatch_abort
dispatch_22:
	lw      $t1 8($a0)
    lw      $t1 16($t1)
    jalr    $t1
	sw      $a0 0($sp)
    addiu   $sp $sp -4

    lw  $a0 -4($fp)
	sw      $a0 0($sp)
    addiu   $sp $sp -4

	move    $a0 $s0
	bnez    $a0 dispatch_23
	la      $a0 str_const9
	li      $t1 188
	jal     _dispatch_abort
dispatch_23:
	lw      $t1 8($a0)
    lw      $t1 32($t1)
    jalr    $t1
	bnez    $a0 dispatch_21
	la      $a0 str_const9
	li      $t1 188
	jal     _dispatch_abort
dispatch_21:
	lw      $t1 8($a0)
    lw      $t1 16($t1)
    jalr    $t1
	addiu	$sp $sp 4

endif2:
    lw  $fp 12($sp)
    lw  $s0 8($sp)
    lw  $ra 4($sp)
    addiu   $sp $sp 16
    jr  $ra

A2I.i2a:
    addiu   $sp $sp -12
    sw  $fp 12($sp)
    sw  $s0 8($sp)
    sw  $ra 4($sp)
    addiu   $fp $sp 4
    move    $s0 $a0
    lw  $a0 12($fp)
    sw      $a0 0($sp)
    addiu   $sp $sp -4
    la  $a0 int_const0
    lw      $t1 4($sp)
    addiu   $sp $sp 4
    move    $t2 $a0
    la      $a0 bool_const1
    beq     $t1 $t2 eq3
    la      $a1 bool_const0
    jal     equality_test
eq3:
	lw      $t1 12($a0)     # bool slot
	beqz    $t1 else_4
    la  $a0 str_const16
	b   endif4
else_4:
    la  $a0 int_const0
	sw		$a0 0($sp)
	addiu	$sp $sp -4
    lw  $a0 12($fp)
	lw		$t1 4($sp)
	addiu	$sp $sp 4
	lw		$t1 12($t1)
	lw		$t2 12($a0)
	la		$a0 bool_const1
	blt	$t1 $t2 compare_0
	la		$a0 bool_const0
compare_0:
	lw      $t1 12($a0)     # bool slot
	beqz    $t1 else_3
    lw  $a0 12($fp)
	sw      $a0 0($sp)
    addiu   $sp $sp -4

	move    $a0 $s0
	bnez    $a0 dispatch_24
	la      $a0 str_const9
	li      $t1 177
	jal     _dispatch_abort
dispatch_24:
	lw      $t1 8($a0)
    lw      $t1 32($t1)
    jalr    $t1
	b   endif3
else_3:
    lw  $a0 12($fp)
	sw		$a0 0($sp)
	addiu	$sp $sp -4
    la  $a0 int_const1
	lw		$t1 12($a0)
	neg		$t1 $t1
	sw		$t1 12($a0)
	jal		Object.copy
	lw		$t1 4($sp)
	addiu	$sp $sp 4
	lw		$t1 12($t1)
	lw		$t2 12($a0)
	mul	$t1 $t1 $t2
	sw		$t1 12($a0)
	sw      $a0 0($sp)
    addiu   $sp $sp -4

	move    $a0 $s0
	bnez    $a0 dispatch_26
	la      $a0 str_const9
	li      $t1 178
	jal     _dispatch_abort
dispatch_26:
	lw      $t1 8($a0)
    lw      $t1 32($t1)
    jalr    $t1
	sw      $a0 0($sp)
    addiu   $sp $sp -4

    la  $a0 str_const17
	bnez    $a0 dispatch_25
	la      $a0 str_const9
	li      $t1 178
	jal     _dispatch_abort
dispatch_25:
	lw      $t1 8($a0)
    lw      $t1 16($t1)
    jalr    $t1
endif3:
endif4:
    lw  $fp 12($sp)
    lw  $s0 8($sp)
    lw  $ra 4($sp)
    addiu   $sp $sp 16
    jr  $ra

A2I.a2i_aux:
    addiu   $sp $sp -12
    sw  $fp 12($sp)
    sw  $s0 8($sp)
    sw  $ra 4($sp)
    addiu   $fp $sp 4
    move    $s0 $a0
	addiu	$sp $sp -4
    la  $a0 int_const0
    sw  $a0 -4($fp)

	addiu	$sp $sp -4
    lw  $a0 12($s0)
	bnez    $a0 dispatch_27
	la      $a0 str_const9
	li      $t1 156
	jal     _dispatch_abort
dispatch_27:
	lw      $t1 8($a0)
    lw      $t1 12($t1)
    jalr    $t1
    sw  $a0 -4($fp)

	addiu	$sp $sp -4
    la  $a0 int_const0
    sw  $a0 -4($fp)

while_1:
    lw  $a0 -4($fp)
	sw		$a0 0($sp)
	addiu	$sp $sp -4
    lw  $a0 12($s0)
	lw		$t1 4($sp)
	addiu	$sp $sp 4
	lw		$t1 12($t1)
	lw		$t2 12($a0)
	la		$a0 bool_const1
	blt	$t1 $t2 compare_1
	la		$a0 bool_const0
compare_1:
	lw		$t1 12($a0)
	beqz	$t1 end_while_1
    lw  $a0 12($s0)
	sw		$a0 0($sp)
	addiu	$sp $sp -4
    la  $a0 int_const10
	jal		Object.copy
	lw		$t1 4($sp)
	addiu	$sp $sp 4
	lw		$t1 12($t1)
	lw		$t2 12($a0)
	mul	$t1 $t1 $t2
	sw		$t1 12($a0)
	sw		$a0 0($sp)
	addiu	$sp $sp -4
    la  $a0 int_const1
	sw      $a0 0($sp)
    addiu   $sp $sp -4
    lw  $a0 -4($fp)
	sw      $a0 0($sp)
    addiu   $sp $sp -4

    lw  $a0 12($s0)
	bnez    $a0 dispatch_29
	la      $a0 str_const9
	li      $t1 160
	jal     _dispatch_abort
dispatch_29:
	lw      $t1 8($a0)
    lw      $t1 20($t1)
    jalr    $t1
	sw      $a0 0($sp)
    addiu   $sp $sp -4

	move    $a0 $s0
	bnez    $a0 dispatch_28
	la      $a0 str_const9
	li      $t1 160
	jal     _dispatch_abort
dispatch_28:
	lw      $t1 8($a0)
    lw      $t1 12($t1)
    jalr    $t1
	jal		Object.copy
	lw		$t1 4($sp)
	addiu	$sp $sp 4
	lw		$t1 12($t1)
	lw		$t2 12($a0)
	add	$t1 $t1 $t2
	sw		$t1 12($a0)
    sw  $a0 12($s0)
    lw  $a0 -4($fp)
	sw		$a0 0($sp)
	addiu	$sp $sp -4
    la  $a0 int_const1
	jal		Object.copy
	lw		$t1 4($sp)
	addiu	$sp $sp 4
	lw		$t1 12($t1)
	lw		$t2 12($a0)
	add	$t1 $t1 $t2
	sw		$t1 12($a0)
    sw  $a0 -4($fp)
	b		while_1
end_while_1:
	li		$a0 0
	addiu	$sp $sp 4

	addiu	$sp $sp 4


    lw  $a0 -4($fp)
	addiu	$sp $sp 4

    lw  $fp 12($sp)
    lw  $s0 8($sp)
    lw  $ra 4($sp)
    addiu   $sp $sp 16
    jr  $ra

A2I.a2i:
    addiu   $sp $sp -12
    sw  $fp 12($sp)
    sw  $s0 8($sp)
    sw  $ra 4($sp)
    addiu   $fp $sp 4
    move    $s0 $a0
    lw  $a0 12($fp)
	bnez    $a0 dispatch_30
	la      $a0 str_const9
	li      $t1 142
	jal     _dispatch_abort
dispatch_30:
	lw      $t1 8($a0)
    lw      $t1 12($t1)
    jalr    $t1
    sw      $a0 0($sp)
    addiu   $sp $sp -4
    la  $a0 int_const0
    lw      $t1 4($sp)
    addiu   $sp $sp 4
    move    $t2 $a0
    la      $a0 bool_const1
    beq     $t1 $t2 eq4
    la      $a1 bool_const0
    jal     equality_test
eq4:
	lw      $t1 12($a0)     # bool slot
	beqz    $t1 else_7
    la  $a0 int_const0
	b   endif7
else_7:
    la  $a0 int_const1
	sw      $a0 0($sp)
    addiu   $sp $sp -4
    la  $a0 int_const0
	sw      $a0 0($sp)
    addiu   $sp $sp -4

    lw  $a0 12($fp)
	bnez    $a0 dispatch_31
	la      $a0 str_const9
	li      $t1 143
	jal     _dispatch_abort
dispatch_31:
	lw      $t1 8($a0)
    lw      $t1 20($t1)
    jalr    $t1
    sw      $a0 0($sp)
    addiu   $sp $sp -4
    la  $a0 str_const17
    lw      $t1 4($sp)
    addiu   $sp $sp 4
    move    $t2 $a0
    la      $a0 bool_const1
    beq     $t1 $t2 eq5
    la      $a1 bool_const0
    jal     equality_test
eq5:
	lw      $t1 12($a0)     # bool slot
	beqz    $t1 else_6
    lw  $a0 12($fp)
	bnez    $a0 dispatch_34
	la      $a0 str_const9
	li      $t1 143
	jal     _dispatch_abort
dispatch_34:
	lw      $t1 8($a0)
    lw      $t1 12($t1)
    jalr    $t1
	sw		$a0 0($sp)
	addiu	$sp $sp -4
    la  $a0 int_const1
	jal		Object.copy
	lw		$t1 4($sp)
	addiu	$sp $sp 4
	lw		$t1 12($t1)
	lw		$t2 12($a0)
	sub	$t1 $t1 $t2
	sw		$t1 12($a0)
	sw      $a0 0($sp)
    addiu   $sp $sp -4
    la  $a0 int_const1
	sw      $a0 0($sp)
    addiu   $sp $sp -4

    lw  $a0 12($fp)
	bnez    $a0 dispatch_33
	la      $a0 str_const9
	li      $t1 143
	jal     _dispatch_abort
dispatch_33:
	lw      $t1 8($a0)
    lw      $t1 20($t1)
    jalr    $t1
	sw      $a0 0($sp)
    addiu   $sp $sp -4

	move    $a0 $s0
	bnez    $a0 dispatch_32
	la      $a0 str_const9
	li      $t1 143
	jal     _dispatch_abort
dispatch_32:
	lw      $t1 8($a0)
    lw      $t1 24($t1)
    jalr    $t1
	lw		$t1 12($a0)
	neg		$t1 $t1
	sw		$t1 12($a0)
	b   endif6
else_6:
    la  $a0 int_const1
	sw      $a0 0($sp)
    addiu   $sp $sp -4
    la  $a0 int_const0
	sw      $a0 0($sp)
    addiu   $sp $sp -4

    lw  $a0 12($fp)
	bnez    $a0 dispatch_35
	la      $a0 str_const9
	li      $t1 144
	jal     _dispatch_abort
dispatch_35:
	lw      $t1 8($a0)
    lw      $t1 20($t1)
    jalr    $t1
    sw      $a0 0($sp)
    addiu   $sp $sp -4
    la  $a0 str_const18
    lw      $t1 4($sp)
    addiu   $sp $sp 4
    move    $t2 $a0
    la      $a0 bool_const1
    beq     $t1 $t2 eq6
    la      $a1 bool_const0
    jal     equality_test
eq6:
	lw      $t1 12($a0)     # bool slot
	beqz    $t1 else_5
    lw  $a0 12($fp)
	bnez    $a0 dispatch_38
	la      $a0 str_const9
	li      $t1 144
	jal     _dispatch_abort
dispatch_38:
	lw      $t1 8($a0)
    lw      $t1 12($t1)
    jalr    $t1
	sw		$a0 0($sp)
	addiu	$sp $sp -4
    la  $a0 int_const1
	jal		Object.copy
	lw		$t1 4($sp)
	addiu	$sp $sp 4
	lw		$t1 12($t1)
	lw		$t2 12($a0)
	sub	$t1 $t1 $t2
	sw		$t1 12($a0)
	sw      $a0 0($sp)
    addiu   $sp $sp -4
    la  $a0 int_const1
	sw      $a0 0($sp)
    addiu   $sp $sp -4

    lw  $a0 12($fp)
	bnez    $a0 dispatch_37
	la      $a0 str_const9
	li      $t1 144
	jal     _dispatch_abort
dispatch_37:
	lw      $t1 8($a0)
    lw      $t1 20($t1)
    jalr    $t1
	sw      $a0 0($sp)
    addiu   $sp $sp -4

	move    $a0 $s0
	bnez    $a0 dispatch_36
	la      $a0 str_const9
	li      $t1 144
	jal     _dispatch_abort
dispatch_36:
	lw      $t1 8($a0)
    lw      $t1 24($t1)
    jalr    $t1
	b   endif5
else_5:
    lw  $a0 12($fp)
	sw      $a0 0($sp)
    addiu   $sp $sp -4

	move    $a0 $s0
	bnez    $a0 dispatch_39
	la      $a0 str_const9
	li      $t1 145
	jal     _dispatch_abort
dispatch_39:
	lw      $t1 8($a0)
    lw      $t1 24($t1)
    jalr    $t1
endif5:
endif6:
endif7:
    lw  $fp 12($sp)
    lw  $s0 8($sp)
    lw  $ra 4($sp)
    addiu   $sp $sp 16
    jr  $ra

A2I.i2c:
    addiu   $sp $sp -12
    sw  $fp 12($sp)
    sw  $s0 8($sp)
    sw  $ra 4($sp)
    addiu   $fp $sp 4
    move    $s0 $a0
    lw  $a0 12($fp)
    sw      $a0 0($sp)
    addiu   $sp $sp -4
    la  $a0 int_const0
    lw      $t1 4($sp)
    addiu   $sp $sp 4
    move    $t2 $a0
    la      $a0 bool_const1
    beq     $t1 $t2 eq7
    la      $a1 bool_const0
    jal     equality_test
eq7:
	lw      $t1 12($a0)     # bool slot
	beqz    $t1 else_17
    la  $a0 str_const16
	b   endif17
else_17:
    lw  $a0 12($fp)
    sw      $a0 0($sp)
    addiu   $sp $sp -4
    la  $a0 int_const1
    lw      $t1 4($sp)
    addiu   $sp $sp 4
    move    $t2 $a0
    la      $a0 bool_const1
    beq     $t1 $t2 eq8
    la      $a1 bool_const0
    jal     equality_test
eq8:
	lw      $t1 12($a0)     # bool slot
	beqz    $t1 else_16
    la  $a0 str_const19
	b   endif16
else_16:
    lw  $a0 12($fp)
    sw      $a0 0($sp)
    addiu   $sp $sp -4
    la  $a0 int_const2
    lw      $t1 4($sp)
    addiu   $sp $sp 4
    move    $t2 $a0
    la      $a0 bool_const1
    beq     $t1 $t2 eq9
    la      $a1 bool_const0
    jal     equality_test
eq9:
	lw      $t1 12($a0)     # bool slot
	beqz    $t1 else_15
    la  $a0 str_const20
	b   endif15
else_15:
    lw  $a0 12($fp)
    sw      $a0 0($sp)
    addiu   $sp $sp -4
    la  $a0 int_const3
    lw      $t1 4($sp)
    addiu   $sp $sp 4
    move    $t2 $a0
    la      $a0 bool_const1
    beq     $t1 $t2 eq10
    la      $a1 bool_const0
    jal     equality_test
eq10:
	lw      $t1 12($a0)     # bool slot
	beqz    $t1 else_14
    la  $a0 str_const21
	b   endif14
else_14:
    lw  $a0 12($fp)
    sw      $a0 0($sp)
    addiu   $sp $sp -4
    la  $a0 int_const4
    lw      $t1 4($sp)
    addiu   $sp $sp 4
    move    $t2 $a0
    la      $a0 bool_const1
    beq     $t1 $t2 eq11
    la      $a1 bool_const0
    jal     equality_test
eq11:
	lw      $t1 12($a0)     # bool slot
	beqz    $t1 else_13
    la  $a0 str_const22
	b   endif13
else_13:
    lw  $a0 12($fp)
    sw      $a0 0($sp)
    addiu   $sp $sp -4
    la  $a0 int_const5
    lw      $t1 4($sp)
    addiu   $sp $sp 4
    move    $t2 $a0
    la      $a0 bool_const1
    beq     $t1 $t2 eq12
    la      $a1 bool_const0
    jal     equality_test
eq12:
	lw      $t1 12($a0)     # bool slot
	beqz    $t1 else_12
    la  $a0 str_const23
	b   endif12
else_12:
    lw  $a0 12($fp)
    sw      $a0 0($sp)
    addiu   $sp $sp -4
    la  $a0 int_const6
    lw      $t1 4($sp)
    addiu   $sp $sp 4
    move    $t2 $a0
    la      $a0 bool_const1
    beq     $t1 $t2 eq13
    la      $a1 bool_const0
    jal     equality_test
eq13:
	lw      $t1 12($a0)     # bool slot
	beqz    $t1 else_11
    la  $a0 str_const24
	b   endif11
else_11:
    lw  $a0 12($fp)
    sw      $a0 0($sp)
    addiu   $sp $sp -4
    la  $a0 int_const7
    lw      $t1 4($sp)
    addiu   $sp $sp 4
    move    $t2 $a0
    la      $a0 bool_const1
    beq     $t1 $t2 eq14
    la      $a1 bool_const0
    jal     equality_test
eq14:
	lw      $t1 12($a0)     # bool slot
	beqz    $t1 else_10
    la  $a0 str_const25
	b   endif10
else_10:
    lw  $a0 12($fp)
    sw      $a0 0($sp)
    addiu   $sp $sp -4
    la  $a0 int_const8
    lw      $t1 4($sp)
    addiu   $sp $sp 4
    move    $t2 $a0
    la      $a0 bool_const1
    beq     $t1 $t2 eq15
    la      $a1 bool_const0
    jal     equality_test
eq15:
	lw      $t1 12($a0)     # bool slot
	beqz    $t1 else_9
    la  $a0 str_const26
	b   endif9
else_9:
    lw  $a0 12($fp)
    sw      $a0 0($sp)
    addiu   $sp $sp -4
    la  $a0 int_const9
    lw      $t1 4($sp)
    addiu   $sp $sp 4
    move    $t2 $a0
    la      $a0 bool_const1
    beq     $t1 $t2 eq16
    la      $a1 bool_const0
    jal     equality_test
eq16:
	lw      $t1 12($a0)     # bool slot
	beqz    $t1 else_8
    la  $a0 str_const27
	b   endif8
else_8:
	move    $a0 $s0
	bnez    $a0 dispatch_40
	la      $a0 str_const9
	li      $t1 129
	jal     _dispatch_abort
dispatch_40:
	lw      $t1 8($a0)
    lw      $t1 0($t1)
    jalr    $t1
    la  $a0 str_const0
endif8:
endif9:
endif10:
endif11:
endif12:
endif13:
endif14:
endif15:
endif16:
endif17:
    lw  $fp 12($sp)
    lw  $s0 8($sp)
    lw  $ra 4($sp)
    addiu   $sp $sp 16
    jr  $ra

A2I.c2i:
    addiu   $sp $sp -12
    sw  $fp 12($sp)
    sw  $s0 8($sp)
    sw  $ra 4($sp)
    addiu   $fp $sp 4
    move    $s0 $a0
    lw  $a0 12($fp)
    sw      $a0 0($sp)
    addiu   $sp $sp -4
    la  $a0 str_const16
    lw      $t1 4($sp)
    addiu   $sp $sp 4
    move    $t2 $a0
    la      $a0 bool_const1
    beq     $t1 $t2 eq17
    la      $a1 bool_const0
    jal     equality_test
eq17:
	lw      $t1 12($a0)     # bool slot
	beqz    $t1 else_27
    la  $a0 int_const0
	b   endif27
else_27:
    lw  $a0 12($fp)
    sw      $a0 0($sp)
    addiu   $sp $sp -4
    la  $a0 str_const19
    lw      $t1 4($sp)
    addiu   $sp $sp 4
    move    $t2 $a0
    la      $a0 bool_const1
    beq     $t1 $t2 eq18
    la      $a1 bool_const0
    jal     equality_test
eq18:
	lw      $t1 12($a0)     # bool slot
	beqz    $t1 else_26
    la  $a0 int_const1
	b   endif26
else_26:
    lw  $a0 12($fp)
    sw      $a0 0($sp)
    addiu   $sp $sp -4
    la  $a0 str_const20
    lw      $t1 4($sp)
    addiu   $sp $sp 4
    move    $t2 $a0
    la      $a0 bool_const1
    beq     $t1 $t2 eq19
    la      $a1 bool_const0
    jal     equality_test
eq19:
	lw      $t1 12($a0)     # bool slot
	beqz    $t1 else_25
    la  $a0 int_const2
	b   endif25
else_25:
    lw  $a0 12($fp)
    sw      $a0 0($sp)
    addiu   $sp $sp -4
    la  $a0 str_const21
    lw      $t1 4($sp)
    addiu   $sp $sp 4
    move    $t2 $a0
    la      $a0 bool_const1
    beq     $t1 $t2 eq20
    la      $a1 bool_const0
    jal     equality_test
eq20:
	lw      $t1 12($a0)     # bool slot
	beqz    $t1 else_24
    la  $a0 int_const3
	b   endif24
else_24:
    lw  $a0 12($fp)
    sw      $a0 0($sp)
    addiu   $sp $sp -4
    la  $a0 str_const22
    lw      $t1 4($sp)
    addiu   $sp $sp 4
    move    $t2 $a0
    la      $a0 bool_const1
    beq     $t1 $t2 eq21
    la      $a1 bool_const0
    jal     equality_test
eq21:
	lw      $t1 12($a0)     # bool slot
	beqz    $t1 else_23
    la  $a0 int_const4
	b   endif23
else_23:
    lw  $a0 12($fp)
    sw      $a0 0($sp)
    addiu   $sp $sp -4
    la  $a0 str_const23
    lw      $t1 4($sp)
    addiu   $sp $sp 4
    move    $t2 $a0
    la      $a0 bool_const1
    beq     $t1 $t2 eq22
    la      $a1 bool_const0
    jal     equality_test
eq22:
	lw      $t1 12($a0)     # bool slot
	beqz    $t1 else_22
    la  $a0 int_const5
	b   endif22
else_22:
    lw  $a0 12($fp)
    sw      $a0 0($sp)
    addiu   $sp $sp -4
    la  $a0 str_const24
    lw      $t1 4($sp)
    addiu   $sp $sp 4
    move    $t2 $a0
    la      $a0 bool_const1
    beq     $t1 $t2 eq23
    la      $a1 bool_const0
    jal     equality_test
eq23:
	lw      $t1 12($a0)     # bool slot
	beqz    $t1 else_21
    la  $a0 int_const6
	b   endif21
else_21:
    lw  $a0 12($fp)
    sw      $a0 0($sp)
    addiu   $sp $sp -4
    la  $a0 str_const25
    lw      $t1 4($sp)
    addiu   $sp $sp 4
    move    $t2 $a0
    la      $a0 bool_const1
    beq     $t1 $t2 eq24
    la      $a1 bool_const0
    jal     equality_test
eq24:
	lw      $t1 12($a0)     # bool slot
	beqz    $t1 else_20
    la  $a0 int_const7
	b   endif20
else_20:
    lw  $a0 12($fp)
    sw      $a0 0($sp)
    addiu   $sp $sp -4
    la  $a0 str_const26
    lw      $t1 4($sp)
    addiu   $sp $sp 4
    move    $t2 $a0
    la      $a0 bool_const1
    beq     $t1 $t2 eq25
    la      $a1 bool_const0
    jal     equality_test
eq25:
	lw      $t1 12($a0)     # bool slot
	beqz    $t1 else_19
    la  $a0 int_const8
	b   endif19
else_19:
    lw  $a0 12($fp)
    sw      $a0 0($sp)
    addiu   $sp $sp -4
    la  $a0 str_const27
    lw      $t1 4($sp)
    addiu   $sp $sp 4
    move    $t2 $a0
    la      $a0 bool_const1
    beq     $t1 $t2 eq26
    la      $a1 bool_const0
    jal     equality_test
eq26:
	lw      $t1 12($a0)     # bool slot
	beqz    $t1 else_18
    la  $a0 int_const9
	b   endif18
else_18:
	move    $a0 $s0
	bnez    $a0 dispatch_41
	la      $a0 str_const9
	li      $t1 111
	jal     _dispatch_abort
dispatch_41:
	lw      $t1 8($a0)
    lw      $t1 0($t1)
    jalr    $t1
    la  $a0 int_const0
endif18:
endif19:
endif20:
endif21:
endif22:
endif23:
endif24:
endif25:
endif26:
endif27:
    lw  $fp 12($sp)
    lw  $s0 8($sp)
    lw  $ra 4($sp)
    addiu   $sp $sp 16
    jr  $ra

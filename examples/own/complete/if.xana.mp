
	#source "if.xana"

' Invocation to the main function
call main
halt


#line 	1

main:
	' Parameters
	' Local variables
	' value :: double (offset -4)
	enter	4

#line 	2
	' If statement
	pushi	1
	pushi	1
	eqi
	jz	label0
	' Body of the if branch

#line 	3
	' Write
	pushb	97
	outb

#line 	3
	' Write
	pushb	108
	outb

#line 	3
	' Write
	pushb	119
	outb

#line 	3
	' Write
	pushb	97
	outb

#line 	3
	' Write
	pushb	121
	outb

#line 	3
	' Write
	pushb	115
	outb

#line 	3
	' Write
	pushb	32
	outb

#line 	3
	' Write
	pushb	116
	outb

#line 	3
	' Write
	pushb	114
	outb

#line 	3
	' Write
	pushb	117
	outb

#line 	3
	' Write
	pushb	101
	outb

#line 	3
	' Write
	pushb	10
	outb
	jmp	label1
label0:
	' Body of the else branch
label1:

#line 	5
	' Write
	pushb	10
	outb

#line 	9
	push	bp
	pushi	-4
	addi
	pushf	1.0
	storef

#line 	10
	' If statement
	pushf	1.0
	push	bp
	pushi	-4
	addi
	loadf
	eqf
	jz	label2
	' Body of the if branch

#line 	11
	' Write
	pushb	116
	outb

#line 	11
	' Write
	pushb	114
	outb

#line 	11
	' Write
	pushb	117
	outb

#line 	11
	' Write
	pushb	101
	outb

#line 	11
	' Write
	pushb	10
	outb
	jmp	label3
label2:
	' Body of the else branch

#line 	13
	' Write
	pushb	102
	outb

#line 	13
	' Write
	pushb	97
	outb

#line 	13
	' Write
	pushb	108
	outb

#line 	13
	' Write
	pushb	115
	outb

#line 	13
	' Write
	pushb	101
	outb

#line 	13
	' Write
	pushb	10
	outb
label3:
	ret	0, 4, 0

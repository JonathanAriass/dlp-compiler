
	#source "examen1.xana"

' Invocation to the main function
call main
halt


#line 	1

f:
	' Parameters
	' a :: int (offset 8)
	' b :: double (offset 4)
	' Local variables
	enter	0

#line 	2
	' Return
	push	bp
	pushi	8
	addi
	loadi
	i2f
	pushi	1
	i2f
	addf
	ret	4, 0, 6

#line 	6

main:
	' Parameters
	' Local variables
	' real :: double (offset -4)
	' integer :: int (offset -6)
	enter	6

#line 	10
	push	bp
	pushi	-6
	addi
	pushb	97
	b2i
	storei

#line 	11
	push	bp
	pushi	-4
	addi
	push	bp
	pushi	-6
	addi
	loadi
	i2f
	storef

#line 	13
	' Write
	push	bp
	pushi	-4
	addi
	loadf
	pushi	1
	i2f
	addf
	outf

#line 	13
	' Write
	pushb	10
	outb

#line 	13
	' Write
	pushb	97
	b2i
	push	bp
	pushi	-6
	addi
	loadi
	subi
	outi

#line 	13
	' Write
	pushb	10
	outb

#line 	13
	' Write
	push	bp
	pushi	-4
	addi
	loadf
	pushb	97
	b2i
	i2f
	divf
	outf

#line 	13
	' Write
	pushb	10
	outb

#line 	13
	' Write
	pushb	97
	b2i
	pushb	98
	b2i
	muli
	outi

#line 	13
	' Write
	pushb	10
	outb

#line 	15
	' Write
	pushb	97
	b2i
	pushb	97
	b2i
	eqi
	outi

#line 	15
	' Write
	pushb	10
	outb

#line 	15
	' Write
	pushb	97
	b2i
	push	bp
	pushi	-6
	addi
	loadi
	gei
	outi

#line 	15
	' Write
	pushb	10
	outb

#line 	15
	' Write
	push	bp
	pushi	-6
	addi
	loadi
	i2f
	push	bp
	pushi	-4
	addi
	loadf
	ltf
	outi

#line 	15
	' Write
	pushb	10
	outb

#line 	15
	' Write
	pushb	97
	b2i
	i2f
	push	bp
	pushi	-4
	addi
	loadf
	pushi	1
	i2f
	addf
	nef
	outi

#line 	15
	' Write
	pushb	10
	outb

#line 	17
	' Write
	pushi	97
	pushb	97
	b2i
	i2f
	call	f
	outf
	ret	0, 6, 0

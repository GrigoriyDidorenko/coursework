%include "io.inc"
extern printf

section .data
rez dd 0
printText1 db 'i am inside if block', 10, 0
printText2 db 'result is %f', 10, 0
section .text
global CMAIN
CMAIN:
mov ebp, esp

push 40c00000h
fld dword[esp]
fchs
fstp dword[esp]
pop eax

push eax
mov eax, 2

mov ebx, eax
pop eax
push eax
cmp ebx, 0
 je ifzero1
 pow1:
 dec ebx
 cmp ebx, 0
 je endpow1
 
 fld dword[esp]
 push eax
 fld dword[esp]
 fmulp
 fstp dword[esp]
 pop eax
 jmp pow1
 ifzero1:
 mov eax, 1
 endpow1:
 mov ebx, eax
 pop eax
 mov eax, ebx


mov dword[rez], eax

mov eax, dword [rez]

push eax
mov eax, 42100000h

mov ebx, eax
pop eax
push ebx
fld dword[esp]
pop ebx
push eax
fld dword[esp]
pop eax
fcomip
jne Lbl2
push dword printText1
call printf

Lbl2:

mov eax, dword [rez]

push eax
fld dword[esp]
pop eax
sub esp, 8
fstp qword[esp]
push dword printText2
call printf

xor eax, eax
mov esp, ebp
ret

%include "io.inc"
extern printf

section .data
counter dd 0
N dd 0
rez dd 0
printText1 db 'So, the factorial of %d is %f', 10, 0
section .text
global CMAIN
CMAIN:
mov ebp, esp

mov eax, 1

mov dword[counter], eax

mov eax, 3f800000h

push eax
fld dword[esp]
fsqrt
fstp dword[esp]
pop eax


mov dword[rez], eax

mov eax, 3f800000h

push eax
fld dword[esp]
fsqrt
fstp dword[esp]
pop eax


mov dword[rez], eax

mov eax, 5

mov dword[N], eax

mov eax, dword [counter]

push eax
mov eax, dword [N]

mov ebx, eax
pop eax
cmp eax, ebx
ja Lbl1
mov eax, dword [counter]
push eax
mov eax, 1

mov ebx, eax
pop eax
add eax, ebx

mov dword[counter], eax

Lbl1:

Lbl4:
mov eax, dword [counter]

push eax
mov eax, dword [N]

mov ebx, eax
pop eax
cmp eax, ebx
ja Lbl3
mov eax, dword [counter]
push eax
mov eax, 1

mov ebx, eax
pop eax
add eax, ebx

mov dword[counter], eax

jmp Lbl4
Lbl3:

mov eax, dword [rez]

push eax
fld dword[esp]
pop eax
sub esp, 8
fstp qword[esp]
mov eax, dword [N]

push eax
push dword printText1
call printf

mov esp, ebp

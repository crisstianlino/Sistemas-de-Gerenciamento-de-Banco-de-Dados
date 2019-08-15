#Cada linha do arquivo será adicionada na variável historias
arq = open('trab.txt', 'r')
historias = []
for linha in arq:
	historias.append(linha.split(','))
arq.close()

def limpar(historia):
	# Retorna a historia sem as operações CP() e FL()
	lista = historia[:]
	while 'CP()' in lista:
		lista.remove('CP()')
	while 'FL()' in lista:
		lista.remove('FL()')
	return lista

	
def descobrir_variaveis(historia):
	# Retorna uma lista de listas das variaveis da historia
	# Ex: [[x,0,0],[y,0,0],[z,0,0]]
	variavel = []
	lista = []
	for i in range(len(historia)):
		if not historia[i][3].isdigit() and historia[i][3] != ")":
			variavel.append(historia[i][3])
	aux = set(variavel)
	aux = list(aux)
	aux.sort()
	for i in range(len(aux)):
		formato = [aux[i],0,0]
		lista.append(formato)
	return lista

def timestamp(Tx, dado, operacao):
	if operacao == 'r':
		if Tx < dado[2]:
			# A transação está tentando ler um dado enquanto outra está escrevendo o dado
			print("")
			print(operacao+""+str(Tx)+"("+dado[0]+")"+"   "+"PROBLEMA ENCONTRADO NA OPERACAO "+operacao+""+str(Tx)+"("+dado[0]+") "+"POIS A TRANSACAO "+str(dado[2])+" VAI ESCREVER O DADO "+dado[0]+".",end='')
			return False
		else:
			dado[1] = Tx
			return True
	else:
		if Tx < dado[1]:
			# A transação está tentando escrever um dado enquanto outra está lendo o dado
			print("")
			print(operacao+""+str(Tx)+"("+dado[0]+")"+"   "+"PROBLEMA ENCONTRADO NA OPERACAO "+operacao+""+str(Tx)+"("+dado[0]+") "+"POIS A TRANSACAO "+str(dado[1])+" JA LEU O DADO "+dado[0]+".",end='')
			return False
		elif Tx < dado[2]:
			# A transação está tentando escrever um dado enquanto outra também está querendo escrever o dado
			print("")
			print(operacao+""+str(Tx)+"("+dado[0]+")"+"   "+"PROBLEMA ENCONTRADO NA OPERACAO "+operacao+""+str(Tx)+"("+dado[0]+") "+"POIS A TRANSACAO "+str(dado[2])+" ESTÁ ESCREVENDO O DADO "+dado[0]+".",end='')
			return False
		else:
			dado[2] = Tx
			return True

def print_entrada(historia):
	print("ENTRADA:")
	for i in historia:
		print(""+i,end=" ")
	print("")

def print_resultado(historia):
	print("\n")
	print("RESULTADO:",end=' ')
	for i in historia:
		print(""+i,end=" ")
	print("")

def print_saida(variavel):
	print("")
	print("SAÍDA:")
	print("        ",end="")
	for i in variavel:
		print("<"+i[0]+","+str(i[1])+","+str(i[2])+">",end=" ")

def shift_right(historia,posicao):
	#O elemento da posição desejada é inserido no inicio e empurra os outros pra direita em uma posição
	aux = []
	time = int(historia[posicao][1])
	condicao = True
	tamanho = len(historia)
	i = 0
	while condicao:
		if i > tamanho-2:
			condicao = False
		if historia[i] == 'BT('+str(time)+')':
			aux.append(historia[i])
			aux.append(historia[posicao])
			i = i+1
		elif i != posicao:
			aux.append(historia[i])
			i = i+1
		else:
			i = i+1
	return aux

def concorrencia(historia):
	print_entrada(historia)
	variaveis = descobrir_variaveis(historia) # variaveis é uma lista que armazena todas as variaveis
	print_saida(variaveis)
	condicao = True
	i = 0
	while condicao:
		if i > len(historia)-2:
			condicao = False
		if historia[i][0] == 'r' or historia[i][0] == 'w':
			Tx = int(historia[i][1])
			letra = historia[i][3]
			for j in range(len(variaveis)):
				if variaveis[j][0] == letra:
					dado = variaveis[j]
			operacao = historia[i][0]
			ts = timestamp(Tx,dado,operacao)
			if ts:
				print("")
				print(historia[i]+"   ",end="")
				for k in variaveis:
					print("<"+k[0]+","+str(k[1])+","+str(k[2])+">",end=" ")
				i = i+1
			else:
				return i
		else:
			i = i+1
	print_resultado(historia)
	recuperar(undo_redo(historia),historia)
	return -1

def undo_redo(historia):
	#Retorna as listas com os numeros das transações que precisam de UNDO e REDO
	trans_redo = []
	trans_undo = []
	retorno = []
	pos_falha = historia.index('FL()')
	i = len(historia) - 1
	cond = True
	while cond:
		if historia[i] == 'FL()':
			cond = False
		elif historia[i][1] == 'M':
			trans_undo.append(int(historia[i][3]))
		i = i-1
	condicao = True
	pos_falha = historia.index('FL()')
	i = pos_falha - 1
	while condicao:
		if historia[i] == 'CP()':
			condicao = False
		elif historia[i][1] == 'M':
			trans_redo.append(int(historia[i][3]))
		else:
			trans_undo.append(int(historia[i][1]))
		i = i-1
	trans_undo = set(trans_undo)
	trans_redo = set(trans_redo)
	trans_undo = trans_undo - trans_redo
	trans_undo = list(trans_undo)
	trans_redo = list(trans_redo)
	trans_undo.sort()
	trans_redo.sort()
	retorno.append(trans_undo)
	retorno.append(trans_redo)
	return retorno

def recuperar(lista_ur,historia):
	print("")
	print("UNDO:")	
	pos_falha = historia.index('FL()')+1
	for i in range(len(lista_ur[0])):
		print("T"+str(lista_ur[0][i]),end=": ")
		for j in range(pos_falha):
			if historia[j][0] == 'w' and historia[j][1] == str(lista_ur[0][i]):
				print(""+historia[j],end=' ')
	print("\n")
	print("REDO:")	
	for i in range(len(lista_ur[1])):
		print("T"+str(lista_ur[1][i]),end=": ")
		for j in range(pos_falha):
			if historia[j][0] == 'w' and historia[j][1] == str(lista_ur[1][i]):
				print(""+historia[j],end=' ')
	print("")
	print("\n------------------------------------------------------------------------------------------------------------------------")
				
#
#MAIN
#

for i in range(len(historias)):
	teste = concorrencia(historias[i])
	tentativas = 0
	print("")
	while teste != -1:
		historias[i] = shift_right(historias[i],teste)
		print("")
		tentativas = tentativas + 1
		print("TENTATIVA "+str(tentativas))
		teste = concorrencia(historias[i])


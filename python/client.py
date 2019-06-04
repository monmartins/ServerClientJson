# -*- coding: utf-8 -*-
import socket
import json
from collections import namedtuple
def _json_object_hook(d): return namedtuple('X', d.keys())(*d.values())
def json2obj(data): return json.loads(data, object_hook=_json_object_hook)
    

HOST = '127.0.0.1'     # Endereco IP do Servidor
PORT = 5000            # Porta que o Servidor esta
tcp = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
dest = (HOST, PORT)
tcp.connect(dest)
print 'Para sair use CTRL+X\n'
msg= ""

numFilm = int(tcp.recv(1024))
tcp.send ("END")
while msg <> '\x18':
    films=[]
    if(numFilm!=0):
        print("Mostrando "+str(numFilm)+" Filmes")
        for i in range(0,numFilm):
            films.append(json2obj(tcp.recv(1024)))
    print(films)
    for i in films:
        print(i)
    print("Insira 1 para mostrar os próximos filmes")
    print("Insira 2 para mostrar os filmes anteriores")
    print("Insira 0 para sair.")
    numFilm=0
    msg = raw_input()
    if(msg == "0"):
        tcp.send("OUT-SERVER")
        break
    elif(msg == "1"):
        tcp.send("NEXT")
    elif(msg == "2"):
        tcp.send("PREVIOUS")
    else:
        print("Tente uma opção válida")
        continue
    # msg = msg + "END"
    # tcp.send (msg)
    # x = json2obj(tcp.recv(1024))
    # print(x.balance)
tcp.close()
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
def transmite(tcp):
    numFilm = int(tcp.recv(2048))
    tcp.send ("END")
    films=[]
    if(numFilm!=0):
        print("Mostrando "+str(numFilm)+" Filme(s)")
        all_string=''
        listFilm=[]
        jsontreat=''
        count_book=0
        while len(all_string.split('---'))<=numFilm:
            receive = tcp.recv(2048).split('|-|')
            for k in receive[:]:
                all_string=all_string+""+k
        listFilm=all_string.split("---")
        for i in listFilm:
            if(len(i.strip())>0):
                print(json2obj(i).filmeID)
    else:
        print("Mostrando "+str(numFilm)+" Filme(s)")

def main():
    print 'Para sair use CTRL+X\n'
    msg= ""
    while msg <> '\x18':
        print("Insira 1 para mostrar os próximos filmes.")
        print("Insira 2 para mostrar os filmes anteriores.")
        print("Insira 0 para sair.")
        numFilm=0
        msg = raw_input()
        if(msg == "0"):
            tcp.send("OUT-SERVER")
            break
        elif(msg == "1"):
            tcp.send("NEXT")
            transmite(tcp)
        elif(msg == "2"):
            tcp.send("PREVIOUS")
            transmite(tcp)
        else:
            print("Tente uma opção válida")
            continue
    tcp.close()

main()
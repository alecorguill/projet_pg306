import requests

r = requests.post('http://localhost:8182/bank/BNP/Bernard')
id = r.text
r = requests.put('http://localhost:8182/bank/BNP/'+id+'/50')

r = requests.get('http://localhost:8182/bank/BNP/'+id)

print "Balance : ", r.text

if __name__ == '__main__':
    pass

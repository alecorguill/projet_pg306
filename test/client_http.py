import requests

# Create account at bnp haribas
r = requests.post('http://localhost:8182/bank/BNP/Bernard')
print r.text

if __name__ == '__main__':
    pass

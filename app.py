from flask import Flask, render_template
from generate_key import generate_RSA_keys
app = Flask(__name__)

@app.route("/")
def main():
    return render_template("index.html")

@app.route('/generateKey')
def generateKey():
    private_key, public_key = generate_RSA_keys()
    return render_template('index.html', public_key=public_key)

if __name__ == "__main__":
    app.run(host='0.0.0.0', port=1025)

from flask import Flask, render_template, send_from_directory, request
import sqlite3
import os
from base64 import b64encode, b64decode
import time

from generate_key import generate_RSA_keys, sign
app = Flask(__name__)

visits_db = 'db/users.db'

UPLOAD_FOLDER = os.path.basename('uploads')
app.config['UPLOAD_FOLDER'] = UPLOAD_FOLDER

@app.route("/")
def main():
    return render_template("index.html")

@app.route('/addUser', methods=['GET', 'POST'])
def addUser():
	private_key, public_key = generate_RSA_keys()
	#print(private_key, public_key)
	conn = sqlite3.connect(visits_db)
	c = conn.cursor()
	c.execute('''INSERT into user_table (first_name, last_name, dob, country, address, public_key) VALUES (?, ?, ?, ?, ?, ?);''',(request.form['inputFirstName'], request.form['inputLastName'], request.form['inputDOB'], request.form['inputCountry'], request.form['inputAddress'], public_key)) #with time
	table1 = c.execute('''SELECT * from user_table;''').fetchall()
	conn.commit() #commit commands
	conn.close()
	print(table1)
	return render_template('index.html')
    
@app.route('/upload', methods=['POST'])
def upload_file():
    file = request.files['datafile']
    f = os.path.join(app.config['UPLOAD_FOLDER'], str(file.filename))
    file.save(f)
    f = open(f, 'rb')
    signature = sign(b64encode(f.read() + str(time.time())))
    return render_template('index.html', signature=signature)

@app.route('/android', methods=['GET'])
def connect_android():
	return "This is the web app"

if __name__ == "__main__":
    app.run(host='0.0.0.0', port=1025)

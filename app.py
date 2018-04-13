from flask import Flask, render_template, send_from_directory
import sqlite3
import os

from generate_key import generate_RSA_keys
app = Flask(__name__)

visits_db = 'db/users.db'

UPLOAD_FOLDER = '/uploads'
app.config['UPLOAD_FOLDER'] = UPLOAD_FOLDER

@app.route("/")
def main():
    return render_template("index.html")

@app.route('/generateKey')
def generateKey():
    private_key, public_key = generate_RSA_keys()
    return render_template('index.html', public_key=public_key)

@app.route('/showSignUp')
def showSignUp():
	# f = open(visits_db, "r")
	# print(f)
	# return "yes"
	conn = sqlite3.connect(visits_db)
	c = conn.cursor()
	c.execute('''INSERT into user_table (first_name, last_name, dob, country, address) VALUES (?,?, ?, ?, ?);''',("Em", "C", "01-01", "US", "MIT")) #with time
	table1 = c.execute('''SELECT * from user_table;''').fetchall()
	conn.commit() #commit commands
	conn.close()
	return str(table1)
	
	# return "here"
	
@app.route('/uploads/<filename>')
def uploaded_file(filename):
    return send_from_directory(app.config['UPLOAD_FOLDER'],
                               filename)

if __name__ == "__main__":
    app.run(host='0.0.0.0', port=1025)

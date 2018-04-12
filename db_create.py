import sqlite3

users_db = "users.db" # just come up with name of database
conn = sqlite3.connect(users_db)  # connect to that database (will create if it doesn't already exist)
c = conn.cursor()  # make cursor into database (allows us to execute commands)
c.execute('''CREATE TABLE user_table (first_name text,last_name text, dob text, country text, address text, public_key text, id integer primary key not null);''') # run a CREATE TABLE command
conn.commit() # commit commands
conn.close() # close connection to database

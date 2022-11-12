import psycopg2
import sys
import os
import random


def main(dir_name):
    conn = None
    images = os.listdir(dir_name)
    random.shuffle(images)
    try:
        conn = psycopg2.connect(host="localhost",
                                port=5432,
                                database="IDA",
                                user="postgres",
                                password="ida2022")
        cur = conn.cursor()

        # execute a statement
        print('Connected successfully')
        print('PostgreSQL database version:')
        cur.execute('SELECT version()')

        # display the PostgreSQL database server version
        db_version = cur.fetchone()
        print(db_version)

        for i in range(len(images)):
            cur.execute("""
            INSERT INTO ida_image(id, filepath) values (%(id)s, %(filepath)s)
            """, {'id': str(i), 'filepath': images[i]})

        conn.commit()
        cur.close()

    except (Exception, psycopg2.DatabaseError) as error:
        print(error)

    finally:
        if conn is not None:
            conn.close()


if __name__ == "__main__":
    assert len(sys.argv) >= 2
    main(sys.argv[1])

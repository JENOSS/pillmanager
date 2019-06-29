import RPi.GPIO as GPIO
import time
import pymysql

conn=pymysql.connect (host="localhost",
                      user="root",
                      passwd="1004",
                      db="medicine")


trig = 5
echo = 3

GPIO.setmode(GPIO.BOARD)
GPIO.setup(trig, GPIO.OUT)
GPIO.setup(echo, GPIO.IN)

while True :
 
    try :

        GPIO.output(trig,False)
        time.sleep(0.5)

        GPIO.output(trig,True)
        time.sleep(0.00001)
        GPIO.output(trig,False)

        while GPIO.input(echo) == 0 :
            pulse_start = time.time()

        while GPIO.input(echo) == 1 :
            pulse_end = time.time()

        pulse_duration = pulse_end - pulse_start
        distance = pulse_duration * 17000
        distance = round(distance,2)
        print("Distance: ",distance,"cm")
        
        if distance < 10 :
            print ("motion dectected") 
            with conn.cursor() as cur :
                select = "SELECT cnt FROM storage WHERE num = 1"
                cur.execute(select)
                button_count = cur.fetchone()
                sql = "UPDATE storage SET cnt = %s + 1 , date = now() WHERE num=1"
                cur.execute(sql,button_count)
                conn.commit()
                time.sleep(3)
    
    except :
        GPIO.cleanup()

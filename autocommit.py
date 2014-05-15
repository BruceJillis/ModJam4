import time, os, winsound, subprocess
from datetime import date

WAIT = 60 * 14.5
START = date(2014, 5, 7)

def beep():
	winsound.Beep(1035, 350)

def do(cmd):
	print(cmd)
	os.system(cmd)

def getCommitMessage():
	delta = START - date.today()
	return "autocommit %s" % time.strftime('day ' + str(delta.days + 1) + ' %H:%M')

def autocommit():
	while True:
		print("git add .")
		subprocess.call(["git", "add", "."])
		msg = getCommitMessage()
		print("git commit %s" % getCommitMessage())
		subprocess.call(["git", "commit", "-m", msg])
		time.sleep(WAIT)
		print("\n\n\n******************************\n")

if __name__ == "__main__":
	try:
		autocommit()
	except:
		print("autocommit script failed!")
		beep()
		beep()
		beep()
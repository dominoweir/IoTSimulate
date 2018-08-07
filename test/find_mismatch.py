f = open("out.txt", "r")
g = open("mismatch.txt", "w")

for line in f.readlines():
	words = line.strip().split(", ")
	for i in range(len(words)):
		print(str(i) + " " + words[i])
	if "null" in words:
		pass
	elif words[2] != words[3]:
		g.write(line)

f.close()
g.close()

import os
os.chdir("C:\\Work\\Thoth")
for filename in os.listdir("."):
	print (filename)
	filename_proper = filename.replace("-", "").lower()
	if filename_proper.startswith("0") or filename_proper.startswith('1') or filename_proper.startswith('2'):
		filename_proper = filename_proper[2:]

	os.rename(filename, filename_proper)
CC ?= gcc

TOBJS ?= test.o incident.o cipher.o

.SUFFIXES: .c .o

TEST = test

all: $(TEST)

$(TEST): $(TOBJS)
	$(CC) -o $(TEST) $(TOBJS) -lm

.c.o:
	$(CC) -c $*.c
	
clean :
	-rm -f *.o $(TEST)
	
check :
	-make
	-./test
	

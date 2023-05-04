


# Description of the problem
The $w_{i}T_{i}$ problem is a single-machine weighted sum-delay problem. It is referred to as $1||\sum w_{i}T_{i}$.
## Mathematical description
We can write the problem $1|w_j,d_j | \sum w_iT_i$ as $1|| \sum w_iT_i$ , because the parameters of the problem are derived from the optimization criterion. We have a set of n tasks performed on the machine.
$J = {1, 2, ..., n}$,
each jth task consists of three parameters:

* $p_j$ - performed time.
* $w_j$ - weight/punishment factor.
* $d_j$ - deadline time.


Each task must be executed without any interrupts for $p_{\pi(j)}$ of machine time. Only one task is executed at a time. Each task should be completed before its desired
completion date $p_{\pi(j)}$, otherwise a penalty will be charged. Tasks do not require preparation because there is no R parameter as in the RPQ problem. Therefore, the first task starts immediately: $S_{\pi(1)} = 0$. The continuity of the machine is preserved. The moment of starting the next
task is always the moment of completion of the previous one:

$$S_{\pi(j)}=C_{\pi(j-1)}$$

For each task, its delay is calculated $T_{\pi(j)} \geq 0$. There is no premium for an earlier completed task. Whether the task gets done well before the requested task completion date or a moment before, it makes no difference, the calculated value will be zero:

$$T_{\pi(j)}=max(C_{\pi(j)} + d_{\pi(j)},0)$$


The optimization criterion under study is the weighted sum of the delays of all tasks:

$$F(\pi)=\sum_{j\in J}^{n} w_{\pi(j)} T_{\pi(j)}$$

**We are looking for such an ordering of the $\pi$ function that the value of the $F(\pi)$ function is as small as possible.**


## Description of input and output data
### Input
The structure of n tasks in which the desired time of completion, execution of the task and the penalty factor for tardiness can be separated.
### Output
Scheduled tasks, giving the smallest possible value of the weighted sum of delays.

## Algorithms for solving the WiTi problem

There are several approaches and options to solve the wiTi problem. The first of these, and the
simplest and at the same time the most costly in terms of time complexity is the
brute force. A much better and slightly more
complex in terms of implementation is an algorithm based on the **dynamic programming method**.
For n input data, this algorithm ensures finding the optimal permutation at exponential computational complexity. Thus, it was possible to run the tests in an acceptable time, even for an input structure containing containing 25 task instance objects.
The dynamic programming method involves dividing a problem into smaller subproblems
and storing their result. Thanks to this, once calculated the value of a part of a permutation of tasks, it does not have to be calculated
once again and this reduces the computational complexity significantly.  The method of dynamic programming for the problem posed can be divided into 2 implementation approaches. The recursive approach
or the iterative approach. My implementation was written based on iteration.
## Implementation
The implementation of the algorithm was written in the object-oriented Java programming language. The creation of task object instances is able to well represent tasks that have information such as the desired time to performed time, weight/punishment factor and deadline time. The structure of object respresenting the task structure is in file Task.java
Important part of main loop in algorithm:
```java
            memory[i] = Integer.MAX_VALUE;
            int sumPerformedTimes = 0;
            int permutationMask = 1;
            int indexOfCurrentTask = 0;
            int bitsConstraint = i;
            final int currentIteration = i;

            final List<Integer> taskIndexes = new ArrayList<>();
            final List<Integer> permutationInMemory = new ArrayList<>();
            
            while (bitsConstraint!=0) {
                if ((currentIteration & permutationMask) != 0) {
                    sumPerformedTimes += tasks[indexOfCurrentTask].getPerformedTime();
                    taskIndexes.add(indexOfCurrentTask);
                    permutationInMemory.add(currentIteration^permutationMask);
                }
                bitsConstraint >>= 1;
                permutationMask <<= 1;
                indexOfCurrentTask++;
            }
```
In order to improve the performance of the program and also to simplify referring to stored values in memory, or at least counting the sum of tasks performed, I decided to use bit operations in the implementation of the algorithm. Bitwise shifts and bitwise symmetric difference were helpful.
Bit shifts were needed indirectly for the next important operations, they gave a constraint in the while loop to check at most the number of combinations stored bitwise. If this constraint had not been shifted, the whole integer would have been considered, i.e. 32 places whether there was a 1 or 0. 32 places because the integer size is 32 bits.
```java
            while (bitsConstraint!=0) {
               // some code
               bitsConstraint >>= 1;
            }
```

A mask was also created, checking by means of a bitwise conjunction operation in the if condition whether there is a 1 or 0 in the bit place under consideration. If 1, the condition was satisfied. 
```java
            while (bitsConstraint!=0) {
                if ((currentIteration & permutationMask) != 0) {
                    // some code 
                }   
                permutationMask <<= 1;
            }
```
In order to find an index in memory storing the necessary pre-calculated values of the weighted average penalty, for the subproblem consisting of the superproblem that is currently being calculated, the implementation uses a bitwise symmetric differential. 
```
memoryOfPermutation.add(currentIteration^permutationMask);
```
_**The easiest way to understand the way of the algorithm working is the analyze the simple example in the next section.**_
In the rest of the implementation there is a reference to the created Lists shown above. Thanks to the use of ArrayList implementation, used operations such as add() and get() have a time complexity of O(1) (in the case of practical sizes of structures for the WiTi problem,
I do not mention much larger sizes of input structures, which would cause the worst-case scenario, when a new array has to be created and all the elements copied to it, since for such sizes, the running time of the algorithm is unacceptable anyway)


## Simple example
This is a simple example of how dynamic programming works for wiTi problem.
It represents finding smallest possible value of the weighted sum of delays for 3 tasks. We have there
7 iterations (0 iteration we can skip, result is always zero), because of what have been said above: time complexity is $2^{n}-1$ = $2^{3}-1$ = 7.
Which current task is currently under computation is defined by 0 or 1.
For example: 1. F(001) means we consider in first iteration only first task,
the second and third are skipped (001 is readen from right to left, like in bitwise).
We are starting from the tiniest subproblems. Then we go through bigger subproblems
using smaller subproblems saved in memory. For example F(101) means, we consider
only first and third task, the second is skipped. In the case of the considered 5th iteration we
have 2 possibilities. If the 3th task is executed first, it means that the next task will
be the 1st, so we should add the performed times of the first and third tasks and then
compute diffrence beetween that sum and delay of first task (because first task is executed after third). This value is multiplied by weight of first task.
Next, value from memory is added, that value was computed in previous iteration when only third task was executed like an only one task in subproblem F(100).
Similarly, for the second possibility in the 5th iteration the calculations occurs.
Then the smallest value is chosen to minimize the criterion of smallest possible
value of the weighted sum of delays.
At the 7th iteration, the situation for the main problem of the 3 tasks is considered.

![simple__example](https://user-images.githubusercontent.com/68119685/236315458-ef1e819f-08c8-42ee-9f8b-a172373dc5ef.png)

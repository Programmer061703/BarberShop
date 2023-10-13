import java.util.concurrent.*;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.List;
import java.util.ArrayList;

public class BarberShop {

    private static Semaphore waitingRoom;
    private static Semaphore barberSemaphore;
    private static Semaphore waitingRoomMutex;
    private static Semaphore barberMutex;
    private static int elapsedTime;
    public static void main(String[] args) {
       //Default values

       int sleepTime = 20000; // Time in miliseconds 
       int numBarbers = 5;
       

       

       if(args.length == 2){
        sleepTime = Integer.parseInt(args[0]);
        sleepTime = sleepTime * 1000;
        numBarbers = Integer.parseInt(args[1]);
        System.out.println("Sleep time: " + sleepTime + " Number of Barbers: " + numBarbers);
        System.out.println("");

       }
       else{
        System.out.println("Usage: java BarberShop <sleep time> <number of barbers>");
        System.out.println("Using default values");
        System.out.println("Sleep time: " + sleepTime + " Number of Barbers: " + numBarbers);
        System.out.println("");
       }
       int numWaitingChairs = (numBarbers * 2);
       int numCustomers = (numBarbers + numWaitingChairs);

       // Initialize/declare variables incuding object of class type Customer, mutexes, and semaphores
       waitingRoom = new Semaphore(numWaitingChairs);
       barberSemaphore = new Semaphore(numBarbers);
       waitingRoomMutex = new Semaphore(1);
       barberMutex = new Semaphore(1);

       //Time info
        long startTime = System.currentTimeMillis();

        List<Thread> threads = new ArrayList<Thread>();
        
        

        for(int i = 0; i < numCustomers; i++){
          elapsedTime =(int) (System.currentTimeMillis() - startTime);

          //Please Note that this will make it so it only displays 1 customer as waiting 
          //This is becuase the arrival time and the cutting time are going be on average the same, and there are 5 barbers
          //But as it is required for customers to arrive at random time between 0 and 1 second this is the best way to do it
          if(elapsedTime < sleepTime){
            //Sleep random time before entering barber shop
            try{
              Thread.sleep(new Random().nextInt(1000));
            }
            catch(InterruptedException e){
              e.printStackTrace();
            }
            
            Customer customer = new Customer(i, waitingRoom, barberSemaphore, waitingRoomMutex, barberMutex);
            Thread thread = new Thread(customer);
            threads.add(thread);
            customer.setThread(thread);
            thread.start();
          }
          else{
            System.out.println("Shop is closed. No new customers allowed.");
            break;
          } 
          
          
        }
        //Join threads
        for(Thread thread : threads){
            try{
                thread.join();
            }
            catch(InterruptedException e){
                e.printStackTrace();
            }
        }

        //All threads have finished
        System.out.println("All customers have been serviced");

        //Calculate time

        long endTime = System.currentTimeMillis();
        long totalTime = endTime - startTime;
        System.out.println("Total time: " + totalTime + " milliseconds");    

        
    }
       
}
     class Customer implements Runnable {

      //Defines variables
      private int id;
      private static int waitNum;
      private static int numInBarber;
      private final Random generator = new Random();

      // define semaphores and mutexs 
      private Semaphore Swait,Sbarber;
      private Semaphore Mbarber, Mwait;
      private Thread thread;

      //Constructor
      public Customer(int id, Semaphore Swait, Semaphore Sbarber, Semaphore Mbarber, Semaphore Mwait){
        this.id = id;
        this.Swait = Swait;
        this.Sbarber = Sbarber;
        this.Mbarber = Mbarber;
        this.Mwait = Mwait;
      }

      //set thread

      public void setThread(Thread thread){
        this.thread = thread;
      }


      private void getWait(){

        try{
          
          if(Swait.tryAcquire()){
            Mwait.acquire();
            waitNum++;
            System.out.println("Customer " + id + " is waiting in the waiting room");
            System.out.println("\tNumb waiting: " + waitNum);
          }
          else{
            System.out.println("Customer " + id + " is leaving the barber shop because the waiting room is full");
            thread.interrupt();
            return;
          }
          
          
          

        } catch(InterruptedException e){
          e.printStackTrace();
        }  
        finally{
          Mwait.release();
        }
      }
      private void getBarberChair(){
        try{

          Sbarber.acquire();
          Swait.release();

          Mbarber.acquire();
          Mwait.acquire();
          numInBarber++;
          waitNum--;

          //Print out num in barber and waitnum
          System.out.println("\t\tCustomer " + id + " is getting a haircut");
          System.out.println("\t\tNumb getting haircut: " + numInBarber);
          

          Mwait.release();
          Mbarber.release();   
          
          
          Thread.sleep(generator.nextInt(1000));
         
        }
        catch(InterruptedException e){
          e.printStackTrace();
        }
        finally{
          Sbarber.release();
        }
      }
    private void exit(){
        Sbarber.acquireUninterruptibly();
        numInBarber--;
        Sbarber.release();
        Mwait.release(); 

        System.out.println("\t\t\tCustomer " + id + " is leaving the barber shop");
        }
        public void run() {
            // Sleep random time before entering barber shop
  
           
            getWait();
            getBarberChair(); // Don't forget to leave the waiting room IF waiting room and barber chair is full
            exit();

            System.out.println("Customer " + id + " has left the barber shop");
        }   
    }




     

    
        
       



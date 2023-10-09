import java.util.concurrent.*;
import java.util.Random;
import java.util.List;
import java.util.ArrayList;

public class BarberShop {

    private static Semaphore waitingRoom;
    private static Semaphore barberSemaphore;
    private static  Semaphore customerSemaphore;


    public static void main(String[] args) {
       //Default values

       int sleepTime = 20;
       int numBarbers = 5;

       if(args.length == 2){
        sleepTime = Integer.parseInt(args[0]);
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

       // Initialize/declare variables incuding object of class type Customer, mutexes, and semaphores

       //Time info
        long startTime = System.currentTimeMillis();

        List<Thread> threads = new ArrayList<Thread>();

        for(int i = 0; i < numCustomers; i++){
          
        }


       
       
        
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

       
        
      }
      private void getBarberChair(){
        

      }
    private void exit(){
            

        }
        public void run() {
            // Sleep random time before entering barber shop
            getWait();
            getBarberChair(); // Don't forget to leave the waiting room IF waiting room and barber chair is full
            exit();

            System.out.println("Customer " + id + " has left the barber shop");
        }

    
        
        
    }




     

    
        
       



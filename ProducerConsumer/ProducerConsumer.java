import java.util.ArrayList;
import java.util.List;
import java.time.LocalDateTime;

class Producer extends Thread{
private static final int MAX_SIZE=3;
List<String> messages = new ArrayList<>();

public void run(){
try{
while(true){
produce();
}
}catch(Exception e){
}
}

synchronized void produce() throws Exception{
if(messages.size()==MAX_SIZE)
{
System.out.println("Queue limit has reached. Waiting for Consumer");
wait();
}
System.out.println("Producer got notification");
String data;
//data = LocalDateTime.now().to_String();
data="data";
messages.add(data);
System.out.println("Producer produced data : " + data);
notify();
}

synchronized String consume() throws Exception{
notify();
if(messages.isEmpty())
{
System.out.println("Queue is empty. Waiting for Producer");
wait();
}
System.out.println("Consumer got notification");
String data;
data = messages.get(0);
messages.remove(data);
return data;
}

}


class Consumer extends Thread{
private Producer producer;
String data;

Consumer(Producer producer)
{
	this.producer=producer;
}

public void run(){
try{
while(true){
data = producer.consume();
System.out.println("Consumer consumed data : " + data);
}
}
catch(Exception e){
}
}
}


class ProducerConsumer {
public static void main(String[] args) throws Exception{
Producer producer = new Producer();
producer.setName("Producer-A");
producer.start();

Consumer consumer = new Consumer(producer);
consumer.setName("Consumer-B");
consumer.start();
}
}


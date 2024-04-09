package edu.iu.svarikot.historyservice.rabbitmq;

import edu.iu.svarikot.historyservice.model.PrimesRecord;
import edu.iu.svarikot.historyservice.repository.PrimesHistoryRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class MQReciever {
    private final PrimesHistoryRepository primesHistoryRepository;

    public MQReciever(PrimesHistoryRepository primesHistoryRepository) {
        this.primesHistoryRepository = primesHistoryRepository;
    }

    @RabbitListener(queues = {"primes"})
    public void recieveMessage(@Payload String message){
        System.out.println(message);
        Gson gson = new Gson();
        PrimesRecord primesRecord= gson.fromJson(message, PrimesRecord.class);
        primesHistoryRepository.save(primesRecord);
    }
}

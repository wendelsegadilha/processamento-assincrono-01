import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Exemplo1Soma {

	private static final ExecutorService threadpool = Executors.newFixedThreadPool(3);

	public static void main(String args[]) throws InterruptedException, ExecutionException {

		GerarNumeroAleatorio tarefa1 = new GerarNumeroAleatorio();
		GerarNumeroAleatorio tarefa2 = new GerarNumeroAleatorio();
		GerarNumeroAleatorio tarefa3 = new GerarNumeroAleatorio();

		System.out.println("Processando a tarefa ...");
		Future<Integer> futureT1 = threadpool.submit(tarefa1);
		Future<Integer> futureT2 = threadpool.submit(tarefa2);
		Future<Integer> futureT3 = threadpool.submit(tarefa3);

		while (!futureT1.isDone() && futureT2.isDone() && futureT3.isDone()) {
			System.out.println("As tarefas ainda não foram processadas!");
			Thread.sleep(1); // sleep for 1 millisecond before checking again
		}
		System.out.println("Tarefa completa!");
		long valor = futureT1.get();
		valor = valor + futureT2.get() + futureT3.get();
		System.out.println("A soma dos valores gerados são:" + valor);
		threadpool.shutdown();
	}

	private static class GerarNumeroAleatorio implements Callable<Integer> {

		@Override
		public Integer call() {
			Random rand = new Random();
			Integer number = rand.nextInt(100);
			System.out.println("Valor Gerado: " + number);
			return number;
		}

	}
}
package it.prova.test;

import java.util.Date;
import java.util.List;

import it.prova.model.Televisore;
import it.prova.service.MyServiceFactory;
import it.prova.service.televisore.TelevisoreService;

public class TestTelevisore {

	public static void main(String[] args) {

		TelevisoreService televisoreService = MyServiceFactory.getTelevisoreServiceImpl();
		try {
			
			testGetById(televisoreService);
			System.out.println("Report presenti:" + televisoreService.listAll().size());
			
			testInserisciNuovo(televisoreService);
			System.out.println("Report presenti:" + televisoreService.listAll().size());
			
			testRimozione(televisoreService);
			System.out.println("Report presenti:" + televisoreService.listAll().size());
			
			testFindByExample(televisoreService);
			System.out.println("Report presenti:" + televisoreService.listAll().size());
			
			testModifica(televisoreService);
			System.out.println("Report presenti:" + televisoreService.listAll().size());

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void testInserisciNuovo(TelevisoreService televisoreService) throws Exception {
		System.out.println(".......testInserisciNuovo inizio.............");

		Televisore nuovoTv = new Televisore("Philips", "HK6", new Date());
		if (televisoreService.inserisciNuovo(nuovoTv) != 1) {
			throw new RuntimeException("testInserisciNuovo fallito...");
		}

		System.out.println("Aggiunto nuovo record");
		System.out.println(".......testInserisciNuovo fine.............");
	}
	
	public static void testGetById(TelevisoreService televisoreService) throws Exception{
		System.out.println(".......testGetById inizio.............");
		
		Televisore televisoreDaPrendereConId = new Televisore("Hitachi", "KOL9", new Date());
		televisoreService.inserisciNuovo(televisoreDaPrendereConId);
		
		List<Televisore> listaTv=televisoreService.listAll();
		Long idPrimoTv = listaTv.get(0).getId();
		
		if(televisoreService.findById(idPrimoTv)==null) {
			throw new RuntimeException("testGetById fallito...");
		}
		
		for(Televisore item:listaTv) {
			televisoreService.rimuovi(item);
		}
		System.out.println(".......testGetById fine.............");
		
	}

	public static void testRimozione(TelevisoreService televisoreService) throws Exception {
		System.out.println(".......testRimozione inizio.............");

		List<Televisore> listaTv = televisoreService.listAll();

		if (listaTv.isEmpty() || listaTv.get(0) == null) {
			throw new RuntimeException("Nulla da rimuovere...");
		}

		Long idPrimoTv = listaTv.get(0).getId();
		Televisore tvDaRimuovere = televisoreService.findById(idPrimoTv);

		if (televisoreService.rimuovi(tvDaRimuovere) != 1)
			throw new RuntimeException("testRimozione fallito ");

		System.out.println("Record rimosso con successo");
		System.out.println(".......testRimozione fine.............");
	}
	
	public static void testFindByExample(TelevisoreService televisoreService) throws Exception{
		System.out.println(".......testFindByExample inizio.............");
		
		televisoreService.inserisciNuovo(new Televisore("Samsung", "TF5", new Date()));
		televisoreService.inserisciNuovo(new Televisore("Samsung", "AL90", new Date()));
		
		List<Televisore> risultatifindByExample = televisoreService.findByExample(new Televisore("Samsung"));
		if (risultatifindByExample.size()!=2)
			throw new RuntimeException("testFindByExample fallito ");
		
		
		for(Televisore item:risultatifindByExample) {
			televisoreService.rimuovi(item);
		}
		
		System.out.println(".......testFindByExample fine.............");
	}
	
	public static void testModifica(TelevisoreService televisoreService) throws Exception{
		System.out.println(".......testModifica inizio.............");
		
		if (televisoreService.inserisciNuovo(new Televisore("Samsung", "TF5", new Date())) != 1)
			throw new RuntimeException("testModifica: inserimento preliminare fallito ");
		
		List<Televisore> risultatifindByExample = televisoreService.findByExample(new Televisore("Samsung"));
		if (risultatifindByExample.size() < 1)
			throw new RuntimeException("testModifica: testFindByExample fallito ");

		Long idTv = risultatifindByExample.get(0).getId();
		String modelloDaModificare="JK90";
		Televisore tvDaModificare=televisoreService.findById(idTv);
		tvDaModificare.setModello(modelloDaModificare);
		
		System.out.println("Televisore candidato alla modifica: " + tvDaModificare);
		if (televisoreService.aggiorna(tvDaModificare) != 1)
			throw new RuntimeException("testModifica fallito ");
		
		for(Televisore item:risultatifindByExample) {
			televisoreService.rimuovi(item);
		}
		System.out.println("Record modificato con successo");
		System.out.println(".......testModifica fine.............");
	}
	
}

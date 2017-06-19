package drools.rules;

import java.util.HashMap;

import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.rule.FactHandle;

/**
 * This is a sample class to launch a rule.
 */
public class DroolsEngine {
	private KieServices ks;
	private KieContainer kContainer;
	private KieSession kSession;
	private HashMap<Object, FactHandle> hechos;
	
    public DroolsEngine(){
        try {
            // load up the knowledge base
	        ks = KieServices.Factory.get();
    	    kContainer = ks.getKieClasspathContainer();
        	kSession = kContainer.newKieSession("ksession-rules");
        	this.hechos = new HashMap<Object, FactHandle>();
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }
    
    public void insertaHechoWithoutFireRules(Object o){
    	FactHandle x = this.kSession.insert(o);
    	this.hechos.put(o, x);
    }
    public void insertaHecho(Object o){
    	FactHandle x = this.kSession.insert(o);
    	this.hechos.put(o, x);
    	this.kSession.fireAllRules();
    }    
    
    public void eliminaHechoWithoutFireRules(Object o){
    	this.kSession.delete(this.hechos.remove(o));
    }
    public void eliminaHecho(Object o){
    	this.kSession.delete(this.hechos.remove(o));
    	this.kSession.fireAllRules();
    } 
    public void fireRules(){
    	this.kSession.fireAllRules();
    }

}

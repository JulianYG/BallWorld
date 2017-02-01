package util;

import java.util.Collection;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * A specific type of dispatcher that dispatch commands to balls.
 */
public abstract class ASetDispatcher<TDispMsg> implements IDispatcher<TDispMsg> {
	
	/**
	 * Stores the observers
	 */
	private final Collection<IObserver<TDispMsg>> observers;
	
	/**
	* The constructor for the class that supplies a CopyOnWriteArraySet instance to the superclass constructor.
	*/
	public ASetDispatcher() {
		this.observers = new CopyOnWriteArraySet<>();
	}
	
	/**
	 * the dispatch implementation needs to be defined in concrete class
	 */
	@Override
	public abstract void dispatch(TDispMsg msg);

	/**
	 * For use of subclasses implementations. 
	 * @return the internal collection of IObservers 
	 */
	public Collection<IObserver<TDispMsg>> getObsCollection() {
		return observers;
	}
	
	/**
	 * Add given observer to internal collection
	 */
	@Override
	public void addObserver(IObserver<TDispMsg> obs) {
		observers.add(obs);
	}

	/** 
	 * Delete the given observer from internal collection
	 */
	@Override
	public void deleteObserver(IObserver<TDispMsg> obs) {
		observers.remove(obs);
	}

	/**
	 * Delete all observers from internal collection
	 */
	@Override
	public void deleteObservers() {
		observers.clear();
	}
}

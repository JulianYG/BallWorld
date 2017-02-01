package util;

/**
 * An dispatcher of messages of type TDispMsg to its registered IObserver'TDispMsg' objects.  
 * The dispatcher is an Observable in the Observer-Observable design pattern
 * though with the difference of always immediately dispatching to the 
 * observers when a message is received.
 * 
 * @author Stephen Wong 
 * @author Derek Peirce
 * @param <TDispMsg> The type of messages being dispatched
 */
public interface IDispatcher<TDispMsg> {

	/**
	 * Dispatch the given message to all the registered Observers
	 * @param iBallCmd  The message to pass to all the observers
	 */
	public void dispatch(TDispMsg iBallCmd);

	/**
	 * Register the given observer in the dispatcher
	 * @param obs  The observer to register
	 */
	public void addObserver(IObserver<TDispMsg> obs);

	/**
	 * De-register the given observer from this dispatcher. 
	 * @param obs  The observer to de-register
	 */
	public void deleteObserver(IObserver<TDispMsg> obs);
	
	/**
	 * De-register all observers from this dispatcher.
	 */
	public void deleteObservers();
}

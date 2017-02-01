package util;

/**
 * A CopyOnWriteArraySet-based IDispatcher that dispatches to its IObservers in parallel
 */
public class BallCmdDispatcher<TDispMsg> extends ASetDispatcher<TDispMsg> {
	/**
	 * Dispatch the message to its observers in parallel
	 */
	@Override
	public void dispatch(TDispMsg msg) {
		getObsCollection().forEach(o -> o.update(this, msg));
	}
}

package paintstrategy;

/**
 * A factory that produces IPaintStrategies
 */
public interface IPaintStrategyFac {

	/**
	 * @return A new IPaintStrategy
	 */
	public IPaintStrategy makePaintStrategy();

}

package Strategy;

public class Task
{
    private Strategy strategy;
    
    public Task(Strategy strategy)
    {
        this.setStrategy(strategy);
    }
    
    public Strategy getStrategy()
    {
        return this.strategy;
    }
    
    public void setStrategy(Strategy strategy)
    {
        this.strategy = strategy;
    }
}

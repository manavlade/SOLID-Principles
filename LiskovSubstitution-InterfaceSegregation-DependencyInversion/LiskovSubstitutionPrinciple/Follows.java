/*
L — Liskov Substitution Principle
Now there's no false promise. Code that needs to withdraw works only against Withdrawable, and FixedDepositAccount was never offered as something that can be withdrawn from in the general sense — so nobody's batch job can call a method that secretly doesn't work.
Interview question: "Give an example where inheritance looks correct conceptually (an FD 'is-an' account) but is wrong from a behavioral contract perspective." — this is precisely the example above. "Is-a" in English doesn't guarantee "is-a" in LSP terms.
*/

interface Withdrawals {
    public void withdrawals(double amount);
}

abstract class Account {
    protected double balance;

    public double getBalance() {
        return balance;
    }
}

class SavingsAccount extends Account implements Withdrawals {
    @Override
    public void withdrawals(double amount) {
        if (amount > balance)
            throw new IllegalArgumentException("Insufficient Balance");
        balance -= amount;
    }
}

class FixedDepositAccount extends Account {
    public void withdrawOnMaturity(double amount) {
        balance -= amount;
    }
}

/*
 * The Real-World Analogy: The Vending Machine
 * Imagine you have a blueprint for a standard Vending Machine.
 * 
 * The Promise: If you put money in and press a button, it either dispenses an
 * item or throws an error saying "Out of Stock".
 * 
 * Every time a technician builds a new type of vending machine (a subclass),
 * they must honor that promise.
 * 
 * Now, someone invents a Mystery Vending Machine (the subclass). They inherit
 * everything from the original blueprint, but they change the button's
 * behavior: instead of dispensing an item or saying "Out of Stock," pressing
 * the button calls the police.
 * 
 * If a customer walks up expecting a regular vending machine, they are in for a
 * terrible surprise. The machine looked like a vending machine, but it didn't
 * behave like one. That is an LSP violation.
 * 
 * The Code Breakdown: Why the First Example Breaks
 * In the first piece of code, the Account class makes a contract (a promise)
 * with the rest of your program:
 * 
 * "Hey, I am an Account. If you call withdraw(), I promise to either take the money out or tell you you're out of cash (IllegalArgumentException)."
 * 
 * Because FixedDepositAccount extends Account, it inherits that promise.
 * 
 * Now, imagine you are a developer writing a automated billing system for a
 * bank. You write code that processes 10,000 accounts at midnight to collect a
 * monthly fee:
 * 
 * Java
 * for (Account acc : allBankAccounts) {
 * try {
 * acc.withdraw(10.00); // The system trusts the Account contract!
 * } catch (IllegalArgumentException e) {
 * System.out.println("Skipping account: No money.");
 * }
 * }
 * This code is perfectly safe if everyone follows the rules. If an account has
 * $0, it catches the error and moves to the next person.
 * 
 * The Disaster: Suddenly, the loop hits a FixedDepositAccount. The loop calls
 * .withdraw(10.00). Instead of withdrawing the money or throwing the expected
 * "Insufficient balance" error, it throws an UnsupportedOperationException.
 * 
 * Because your loop didn't expect and didn't catch this completely different
 * exception, the entire automated billing system crashes and halts halfway
 * through. 9,000 customers don't get processed because one subclass lied about
 * what it could do.
 * 
 * The Fix: Be Honest About Capabilities
 * The fixed code solves this by admitting that a Fixed Deposit account cannot
 * be treated the same way as a regular checking or savings account.
 * 
 * Instead of forcing FixedDepositAccount to inherit a withdraw() method it
 * can't actually use, we split the behavior:
 * 
 * Account: Just holds a balance. Both Savings and Fixed Deposits are accounts.
 * 
 * Withdrawable: A special "badge" (interface) that says,
 * "You can pull money out of me anytime."
 * 
 * Now, our automated billing system changes its loop to only look for things
 * that wear the Withdrawable badge:
 * 
 * Java
 * for (Withdrawable acc : onlyWithdrawableAccounts) {
 * acc.withdraw(10.00); // 100% safe! No Fixed Deposit accounts can ever sneak
 * in here.
 * }
 * The Core Lesson
 * Liskov means: Don't inherit from a class just because it feels related. Only
 * inherit if the subclass can seamlessly stand in for the parent class without
 * the rest of the program needing to treat it like a special, fragile
 * snowflake.
 * 
 */

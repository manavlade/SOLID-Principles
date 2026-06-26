/*
This compiles fine. It even looks reasonable 
FDs genuinely can't be withdrawn early. But it violates LSP: 
anywhere your codebase has Account acc = ...; acc.withdraw(500);
that code was written trusting the promise that Account.withdraw() 
either succeeds or fails with IllegalArgumentException for insufficient 
funds. Now it can suddenly throw UnsupportedOperationException — 
a completely different failure mode the caller never expected and didn't catch.
Real consequence: imagine a batch job that loops over 10,000 accounts 
calling withdraw() for a standing-instruction debit. It catches
 IllegalArgumentException to handle insufficient-balance cases gracefully. 
 The moment an FD account is in that batch, the job crashes instead of gracefully 
 skipping — because FixedDepositAccount lied about what an Account is allowed to do.
*/

class Account {

    protected double balance;;

    public void withdraw(double amount) {
        if (amount > balance)
            throw new IllegalArgumentException("Insufficient balance");
        balance -= amount;
    }
}

class FixedDeposite extends Account {
    @Override
    public void withdraw(double amount) {
        throw new UnsupportedOperationException("Fixed deposite cannot be withdrawn");
    }
}

public class Violates {

    public static void main(String[] args) {

    }

}

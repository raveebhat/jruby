package org.jruby.compiler.ir.instructions;

import org.jruby.compiler.ir.Operation;
import org.jruby.compiler.ir.operands.Operand;
import org.jruby.compiler.ir.operands.Variable;
import org.jruby.compiler.ir.IRMethod;
import org.jruby.compiler.ir.representations.InlinerInfo;

// Rather than building a zillion instructions that capture calls to ruby implementation internals,
// we are building one that will serve as a placeholder for internals-specific call optimizations.
public class RubyInternalCallInstr extends CallInstr {
    public RubyInternalCallInstr(Variable result, Operand methAddr, Operand receiver,
            Operand[] args) {
        super(Operation.RUBY_INTERNALS, result, methAddr, receiver, args, null);
    }

    public RubyInternalCallInstr(Variable result, Operand methAddr, Operand receiver,
            Operand[] args, Operand closure) {
        super(result, methAddr, receiver, args, closure);
    }

    @Override
    public boolean isRubyInternalsCall() {
        return true;
    }

    @Override
    public boolean isStaticCallTarget() {
        return true;
    }

    // SSS FIXME: Dont optimize these yet!
    @Override
    public IRMethod getTargetMethodWithReceiver(Operand receiver) {
        return null;
    }

    @Override
    public Instr cloneForInlining(InlinerInfo ii) {
        return new RubyInternalCallInstr(ii.getRenamedVariable(result),
                _methAddr.cloneForInlining(ii), getReceiver().cloneForInlining(ii),
                cloneCallArgs(ii), _closure == null ? null : _closure.cloneForInlining(ii));
    }
}
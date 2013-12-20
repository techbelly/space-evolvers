package com.tenikya.evolve;

/**
 * Insert the type's description here.
 * Creation date: (03/12/00 19:29:09)
 * @author: Administrator
 */
class Brain {
    protected int NumInputs = 8;
    protected int NumHidden = 8;
    protected int NumOutputs = 4;

    public double Inputs[];
    public double Hidden[];
    public double Outputs[];

    public double W1[][];
    public double W2[][];

    /**
     * Brain constructor comment.
     */
    public Brain(FloatGenes genes) {
        Inputs = new double[NumInputs];
        Outputs = new double[NumOutputs];
        Hidden = new double[NumHidden];

        W1 = new double[NumInputs][NumHidden];
        W2 = new double[NumHidden][NumOutputs];

        setWeights(genes);
    }

    public void setWeights(FloatGenes genes) {
        int count = 0;
        for (int i = 0; i < NumInputs; i++) {
            for (int h = 0; h < NumHidden; h++) {
                W1[i][h] = genes.GetGene(count);
                count++;
            }
        }
        for (int i = 0; i < NumHidden; i++) {
            for (int h = 0; h < NumOutputs; h++) {
                W2[i][h] = genes.GetGene(count);
                count++;
            }
        }
    }

    public void forwardPass() {
        int i, h, o;
        for (h = 0; h < NumHidden; h++) {
            Hidden[h] = 0.0;
        }
        for (i = 0; i < NumInputs; i++) {
            for (h = 0; h < NumHidden; h++) {
                Hidden[h] += Inputs[i] * W1[i][h];
            }
        }
        for (o = 0; o < NumOutputs; o++) {
            Outputs[o] = 0.0;
        }
        for (h = 0; h < NumHidden; h++) {
            for (o = 0; o < NumOutputs; o++) {
                Outputs[o] += Sigmoid(Hidden[h]) * W2[h][o];
            }
        }
        for (o = 0; o < NumOutputs; o++) {
            Outputs[o] = Sigmoid(Outputs[o]);
        }
    }

    protected double Sigmoid(double x) {
        return ((1.0 / (1.0 + Math.exp(-x))) - 0.5);
    }

    public void decisionTime(double x, double y, double z, double w) {
        Inputs[0] = x;
        Inputs[1] = y;
        Inputs[2] = z;
        Inputs[3] = w;
        forwardPass();
    }

    public boolean fireLeftEngine() {
        return (Outputs[0] > Threshold);
    }

    public boolean fireRightEngine() {
        return (Outputs[1] > Threshold);
    }

    public boolean fireThruster() {
        return (Outputs[2] > Threshold);
    }

    public boolean fireGun() {
        return (Outputs[3] > Threshold);
    }

    public double Threshold = 0;

    public void decisionTime(double x, double y, double z, double w, double u, double v) {
        Inputs[0] = x;
        Inputs[1] = y;
        Inputs[2] = z;
        Inputs[3] = w;
        Inputs[4] = u;
        Inputs[5] = v;
        forwardPass();
    }

    public void decisionTime(double x, double y, double z, double w, double u, double v, double k, double i) {
        Inputs[0] = x;
        Inputs[1] = y;
        Inputs[2] = z;
        Inputs[3] = w;
        Inputs[4] = u;
        Inputs[5] = v;
        Inputs[6] = k;
        Inputs[7] = i;
        forwardPass();
    }
}
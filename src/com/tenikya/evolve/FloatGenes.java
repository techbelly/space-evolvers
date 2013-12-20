package com.tenikya.evolve;

class FloatGenes {
    protected int numGenes;
    protected double genes[];
    static double mutateFactor = 0.1;
    static double mutatePercent = 0.05;
    static double inheritPercent = 0.9;

    /**
     * FloatGenes constructor comment.
     */
    public FloatGenes(int number) {
        numGenes = number;
        genes = new double[number];
        for (int i = 0; i < (numGenes - 4); i++) {
            SetGene(i, Math.random() - 0.5);
        }
        double leftpower = Math.random();
        double rightpower = Math.random();
        double uppower = Math.random();
        double firepower = Math.random();

        double sum = leftpower + rightpower + uppower + firepower;

        SetGene(numGenes - 4, leftpower / sum);
        SetGene(numGenes - 3, rightpower / sum);
        SetGene(numGenes - 2, uppower / sum);
        SetGene(numGenes - 2, firepower / sum);
    }


    public double GetGene(int gene) {
        return genes[gene];
    }

    public void MutateFrom(FloatGenes progenitor) {
        for (int i = 0; i < numGenes; i++) {
            double newGene = progenitor.GetGene(i);
            double thisGene = this.GetGene(i);
            if (Math.random() < inheritPercent) {
                // check if gene's inherited
                thisGene = newGene; // we can do this because double's a built-in
            }
            if (Math.random() < mutatePercent) {
                // should we mutate it?
                thisGene = thisGene + (Math.random() * mutateFactor) - mutateFactor / 2;
            }
            SetGene(i, thisGene);
        }
    }

    public void SetGene(int gene, double value) {
        genes[gene] = value;
    }
}

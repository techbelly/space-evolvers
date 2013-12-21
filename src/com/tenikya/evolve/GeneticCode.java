package com.tenikya.evolve;

// implements a chromosome of length x, with 4 bits to each gene.





class GeneticCode extends Object {

    protected int numGenes;
    protected int numbits;
    protected int genes[];
    static protected int geneLength;
    static protected int mask; // (2^GENE_LENGTH)-1
    protected double mutatePercent = 0.4;
    protected double inheritPercent = 0.4;


    public GeneticCode(int number, int length) {
        numGenes = number;
        geneLength = length;

        numbits = geneLength * numGenes;
        genes = new int[numbits / 16 + 1];
        mask = (int) (Math.pow(2, geneLength) - 1);

        for (int i = 1; i <= numGenes; i++) {
            SetGene(i, (int) (Math.random() * 15));
        }

    }

    public int GetGene(int gene) {
        int site = ((gene - 1) * geneLength) % 16;
        int geneIndex = ((gene - 1) * geneLength) / 16;
        return (int) (genes[geneIndex] & (mask << site)) >>> site;
    }

    public void SetGene(int gene, int value) {
        int site = ((gene - 1) * geneLength) % 16;
        int geneIndex = ((gene - 1) * geneLength) / 16;

        int tempgene = GetGene(gene);

        int mask = tempgene << site;

        genes[geneIndex] = genes[geneIndex] ^ mask;
        genes[geneIndex] = genes[geneIndex] | (value << site);
    }

    public void MutateFrom(GeneticCode progenitor) {
        for (int i = 1; i <= numGenes; i++) {
            int newGene = progenitor.GetGene(i);
            int finalGene = this.GetGene(i);
            if (Math.random() < inheritPercent) {
                finalGene = newGene;
            }

            if (Math.random() < mutatePercent) {
                finalGene = finalGene + (int) (Math.random() * 4 - 2);
                finalGene = finalGene % 16;

            }
            SetGene(i, finalGene);
        }

    }

    public void setMutate(double d) {
        if (d > 1) {
            d = 1;
        }
        if (d < 0) {
            d = 0;
        }

        mutatePercent = d;
    }

    public double getMutate() {
        return mutatePercent;
    }

    public double getInherit() {
        return inheritPercent;
    }

    public void setInherit(double d) {
        if (d > 1) {
            d = 1;
        }
        if (d < 0) {
            d = 0;
        }
        inheritPercent = d;
    }

}
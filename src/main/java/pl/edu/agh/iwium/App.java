package pl.edu.agh.iwium;

import agents.TwoPlayerAgent;
import algorithms.ISelector;
import algorithms.QLearningSelector;
import algorithms.RandomSelector;
import pl.edu.agh.iwium.blackjack.bot.Bots;
import pl.edu.agh.iwium.blackjack.piqle.BlackjackEnvironment;
import pl.edu.agh.iwium.texasholdem.piqle.TexasholdemEnvironment;
import referees.TwoPlayerReferee;

import java.util.Optional;

@SuppressWarnings("unused")
public class App {

	public static void main(String[] args) {
//        blackjackFindOptimalParams(200, 10, Bots.DEALER_STRATEGY);
        blackjack(Optional.of(0.3), Optional.of(0.7), Optional.of(0.8), Bots.RANDOM);
	}

	private static void texasholdem() {
		TexasholdemEnvironment env = new TexasholdemEnvironment();

		QLearningSelector qlearning1 = new QLearningSelector();
		ISelector qlearning2 = new RandomSelector();

		double epsilon = 0.9;
		qlearning1.setEpsilon(epsilon);
        qlearning1.setAlpha(0.7);
		qlearning1.setGeometricAlphaDecay();
        qlearning1.setGamma(9.0);


		TwoPlayerAgent p1 = new TwoPlayerAgent(env, qlearning1);
		TwoPlayerAgent p2 = new TwoPlayerAgent(env, qlearning2);
		// TwoPlayerAgent p2 = new TwoPlayerAgent(env, new RandomSelector());
//		TwoPlayerAgent p2 = new TwoPlayerAgent(env, new BlackjackDealerStrategy());

		TwoPlayerReferee arbitre = new TwoPlayerReferee(p1, p2);
		// arbitre.setVerbosity();
		int resu[] = new int[3];

		for (int i = 1; i < 20000; i++) {
			epsilon *= 0.99999;
			qlearning1.setEpsilon(epsilon);
			resu[arbitre.episode() + 1]++;
			if (i % 100 == 0) {
				System.out.println(i + " " + resu[0] + " " + resu[1] + " " + resu[2]);
				resu = new int[3];
			}
		}

//		for (int i = 1; i < 100; i++) {
//			epsilon *= 0.99999;
//			qlearning1.setEpsilon(epsilon);
//
//			resu[arbitre.episode() + 1]++;
//			System.out.println(resu[0] + " " + resu[1] + " " + resu[2]);
//			resu = new int[3];
//		}
	}
	
	private static void blackjack(Optional<Double> epsilonOptional, Optional<Double> alphaOptional, Optional<Double> gammaOptional, ISelector selector) {
		BlackjackEnvironment env = new BlackjackEnvironment();
		QLearningSelector qlearning1 = new QLearningSelector();
        qlearning1.setGeometricAlphaDecay();

        epsilonOptional.ifPresent(qlearning1::setEpsilon);
        alphaOptional.ifPresent(qlearning1::setAlpha);
        gammaOptional.ifPresent(qlearning1::setGamma);


		TwoPlayerAgent p1 = new TwoPlayerAgent(env, qlearning1);
        TwoPlayerAgent p2 = new TwoPlayerAgent(env, selector);

		TwoPlayerReferee arbitre = new TwoPlayerReferee(p1, p2);
		// arbitre.setVerbosity();
		int resu[] = new int[3];

		for (int i = 1; i <= 1000; i++) {
			qlearning1.setEpsilon(qlearning1.getEpsilon() * 0.79999);
			resu[arbitre.episode() + 1]++;
			if (i % 1000 == 0) {
				System.out.println(i + " " + resu[0] + " " + resu[1] + " " + resu[2]);
				resu = new int[3];
			}
		}
//		for (int i = 1; i < 10; i++) {
//			epsilon *= 0.99999;
//			qlearning1.setEpsilon(epsilon);
//			qlearning2.setEpsilon(epsilon);
//
//			resu[arbitre.episode() + 1]++;
//			System.out.println(resu[0] + " " + resu[1] + " " + resu[2]);
//			resu = new int[3];
//		}
	}

    private static void blackjackFindOptimalParams(int maxIter, int epochSize, ISelector selector) {
        int bestResult = 0;
        double bestEps = 0.0;
        double bestAlpha = 0.0;
        double bestGamma = 0.0;

        for (double eps = 0.0; eps < 1.05; eps+=0.1) {
            for (double alpha = 0.0; alpha < 1.05; alpha+=0.1) {
                for (double gamma = 0.0; gamma < 1.05; gamma+=0.1) {
                    BlackjackEnvironment env = new BlackjackEnvironment();
                    QLearningSelector qlearning1 = new QLearningSelector();
                    qlearning1.setGeometricAlphaDecay();
                    TwoPlayerAgent p1 = new TwoPlayerAgent(env, qlearning1);
                    TwoPlayerAgent p2 = new TwoPlayerAgent(env, selector);
                    TwoPlayerReferee arbitre = new TwoPlayerReferee(p1, p2);
                    int resu[] = new int[3];

                    System.out.println("Eps: " + eps + " Alpha: " + alpha + " Gamma: " + gamma);
                    qlearning1.setEpsilon(eps);
                    qlearning1.setAlpha(alpha);
                    int win = 0;
                    for (int i = 1; i < maxIter; i++) {
                        qlearning1.setEpsilon(qlearning1.getEpsilon() * 0.99999);
                        resu[arbitre.episode() + 1]++;
                        if (i % epochSize == 0) {
                            win = resu[0] + resu[1] >= win ? win+1 : win;
//                            win = resu[0] + resu[1];
                            resu = new int[3];
                        }
                    }
                    if (bestResult <= win) {
                        bestResult = win;
                        bestEps = eps;
                        bestAlpha = alpha;
                        bestGamma = gamma;
                    }
                }
            }
        }

        System.out.println("BestResult: " + bestResult);
        System.out.println("BestEps: " + bestEps);
        System.out.println("BestAlpha: " + bestAlpha);
        System.out.println("BestGamma: " + bestGamma);

    }
}

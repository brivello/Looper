/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package looper;

import java.util.Random;
import com.jsyn.JSyn;
import com.jsyn.Synthesizer;
import com.jsyn.swing.ExponentialRangeModel;
import com.jsyn.swing.PortControllerFactory;
import com.jsyn.swing.PortModelFactory;
import com.jsyn.swing.RotaryTextController;
import com.jsyn.unitgen.LineOut;
import com.jsyn.unitgen.LinearRamp;
import com.jsyn.unitgen.SawtoothOscillatorBL;
import com.jsyn.unitgen.SineOscillator;
import com.jsyn.unitgen.UnitOscillator;
import javax.swing.JPanel;
import java.lang.Math;
/**
 *
 * @author brive
 */
public class Looper {
    private Synthesizer synth;
    private Synthesizer synth2;
    private Synthesizer melSynth;
    private LineOut lineOut;
    private LineOut lineOut2;
    private LineOut melOut;
    private SawtoothOscillatorBL osc;
    private SineOscillator osc2;
    private SawtoothOscillatorBL melOsc;
    private LinearRamp lag;
    private LinearRamp lag2;
    private LinearRamp melLag;
    private double BPM;
    private double Beat; //time for 1 beat(s)
    private double hexiBeat; // 1/16th of a beat
    private double split;
    private double keyFreq;
    private double keyFreq2;
       
       public Looper (double BPM){
           synth = JSyn.createSynthesizer();
           synth2= JSyn.createSynthesizer();
           melSynth=JSyn.createSynthesizer();
           
        // Add a tone generator.
               
		synth.add(osc= new SawtoothOscillatorBL());
                synth2.add(osc2= new SineOscillator());
                melSynth.add(melOsc=new SawtoothOscillatorBL());
                //osc= new SawtoothOscillatorBL();
                
		// Add a lag to smooth out amplitude changes and avoid pops.
		synth.add( lag = new LinearRamp() );
                synth2.add(lag2 =new LinearRamp());
                
                melSynth.add(melLag=new LinearRamp());
		// Add an output mixer.
		synth.add( lineOut = new LineOut() );
                synth2.add(lineOut2= new LineOut());
                melSynth.add(melOut=new LineOut());
		// Connect the oscillator to the output.
		osc.output.connect( 0, lineOut.input, 0 );
                osc2.output.connect( 0, lineOut2.input, 0 );
                melOsc.output.connect(0, melOut.input, 0);
                
		
		// Set the minimum, current and maximum values for the port.
		lag.output.connect( osc.amplitude );
                lag2.output.connect( osc2.amplitude );
                melLag.output.connect( melOsc.amplitude);
                
		lag.input.setup( 0.0, 0.3, 1.0 );
                lag2.input.setup( 0.0, 0.7, 1.0 );
                melLag.input.setup(0.0, 0.3, 1.0);
                
		lag.time.set(  0.2 );
		lag2.time.set(  0.2 );
                melLag.time.set(0.2);

		// Arrange the faders in a stack.

		ExponentialRangeModel amplitudeModel = PortModelFactory.createExponentialModel( lag.input );
		RotaryTextController knob = new RotaryTextController( amplitudeModel, 5 );
		JPanel knobPanel = new JPanel();
		knobPanel.add( knob );;

            osc.frequency.setup( 50.0, 300.0, 10000.0 );
            this.synth=synth;
            this.lineOut=lineOut;
            this.osc=osc;
            this.keyFreq=modulator(440,-1);
            osc.frequency.set(keyFreq*.5);
            osc2.frequency.set(keyFreq);
            melOsc.frequency.set(modulator(keyFreq, 7));
            this.lag=lag;
            this.BPM=BPM;
            this.Beat=60/BPM;
            this.hexiBeat=Beat/16;
            this.split=8.0;
       }
    public void Loop1 () throws InterruptedException{
        double [] aTriad=majorTriadArray(modulator(keyFreq,-24), 16);                
        double [] gTriad=majorTriadArray(modulator(keyFreq,-27),18);
        Random rand=new Random();
        
        synth2.start();
        synth.start();
        melSynth.start();
        lineOut2.start();
        lineOut.start();
       
       
        
        osc2.amplitude.set(0.00);   
        double time = synth.getCurrentTime();
        double baseFreq=modulator(osc.frequency.get(),-12);
        while(time>=0){
            int i=4;
            
            
          
            if (time==Beat*16){
                melOsc.frequency.set(modulator(keyFreq,4));
                melOut.start();}
            if (time==Beat*18){
                melOsc.frequency.set(modulator(melOsc.frequency.get(),3));}
            if (time==Beat*20){
                melOsc.frequency.set(modulator(melOsc.frequency.get(),5));}
            if (time==Beat*22){
                melOsc.frequency.set(modulator(melOsc.frequency.get(),4));}
            if (time==Beat*23){
                melOsc.frequency.set(modulator(melOsc.frequency.get(),-2));}
            
            
            if (time==Beat*24){
                melOsc.frequency.set(modulator(melOsc.frequency.get(),3));}
            if (time==Beat*25){
               melOsc.frequency.set(modulator(melOsc.frequency.get(),-7));}
            if (time==Beat*26){
                melOsc.frequency.set(modulator(melOsc.frequency.get(),-5));}
            if (time==Beat*30){
                melOsc.frequency.set(modulator(keyFreq, 7));}
            
            
            
            if (time==Beat*32){
                melOsc.frequency.set(keyFreq);}
            if (time ==Beat*35){
                melOsc.frequency.set(modulator(melOsc.frequency.get(),-5));}
            if (time ==Beat*36){
                melOsc.frequency.set(keyFreq);}
            if (time ==Beat*39){
                melOsc.frequency.set(modulator(melOsc.frequency.get(),-5));}
            
            
            
            if (time==Beat*40){
                melOsc.frequency.set(modulator(melOsc.frequency.get(),-2));}
            if (time==Beat*41){
                melOsc.frequency.set(modulator(keyFreq, -7));}
            if (time==Beat*42){
                melOsc.frequency.set(modulator(melOsc.frequency.get(),-3));}
            if (time==Beat*43){
               melOsc.frequency.set(modulator(melOsc.frequency.get(),+3));}
            if (time==Beat*44){
                melOsc.frequency.set(modulator(melOsc.frequency.get(),+5));}
            if (time==Beat*45){
                melOsc.frequency.set(keyFreq);}
            if (time==Beat*46){
                melOut.stop();}
            
            
            
            if (time==Beat*48){
                melOsc.frequency.set(modulator(keyFreq,4));
                melOut.start();}
            if (time==Beat*50){
                melOsc.frequency.set(modulator(melOsc.frequency.get(),3));}
            if (time==Beat*52){
                melOsc.frequency.set(modulator(melOsc.frequency.get(),5));}
            if (time==Beat*54){
                melOsc.frequency.set(modulator(melOsc.frequency.get(),4));}
            if (time==Beat*55){
                melOsc.frequency.set(modulator(melOsc.frequency.get(),-2));}
            
            if (time==Beat*56){
                melOsc.frequency.set(modulator(melOsc.frequency.get(),3));}
            if (time==Beat*57){
                melOsc.frequency.set(modulator(melOsc.frequency.get(),2));}
            if (time==Beat*58){
                melOsc.frequency.set(modulator(melOsc.frequency.get(),4));}
            if (time==Beat*59){
                melOsc.frequency.set(modulator(melOsc.frequency.get(),-2));}
            if (time==Beat*60){
                melOsc.frequency.set(modulator(melOsc.frequency.get(),-5));}
            if (time==Beat*61){
                melOsc.frequency.set(modulator(melOsc.frequency.get(),2));}
            if (time==Beat*62){
                melOsc.frequency.set(modulator(melOsc.frequency.get(),4));}
            if (time==Beat*63){
                melOsc.frequency.set(modulator(melOsc.frequency.get(),-2));}
            
            
            if (time==Beat*64){
                melOsc.frequency.set(modulator(keyFreq,12));}
            if (time==Beat*65){
                melOsc.frequency.set(modulator(melOsc.frequency.get(),2));}
            if (time==Beat*66){
                melOsc.frequency.set(modulator(melOsc.frequency.get(),-5));}
            if (time==Beat*67){
                melOsc.frequency.set(modulator(melOsc.frequency.get(),-2));}
            if (time==Beat*68){
                melOsc.frequency.set(keyFreq);}
            if (time==Beat*70){
                melOut.stop();}
            if (time==Beat*71){
                melOsc.frequency.set(modulator(melOsc.frequency.get(),-7));
                melOut.start();}
            
            
            if (time==Beat*72){
                melOsc.frequency.set(modulator(melOsc.frequency.get(),+5));
                melOut.start();}
            if (time==Beat*73){
                melOsc.frequency.set(modulator(melOsc.frequency.get(),2));}
            if (time==Beat*74){
                melOsc.frequency.set(modulator(melOsc.frequency.get(),-5));}
            if (time==Beat*75){
                melOsc.frequency.set(modulator(melOsc.frequency.get(),-2));}
            if (time==Beat*79){
                melOut.stop();}
            
            
            if (time==Beat*81){
                melOsc.frequency.set(modulator(keyFreq,-12));
                melOut.start();}
            
            
            if (time==Beat*89){
                melOsc.frequency.set(modulator(melOsc.frequency.get(),2));}
            if (time==Beat*90){
                melOsc.frequency.set(modulator(melOsc.frequency.get(),3));}
            if (time==Beat*91){
                melOsc.frequency.set(modulator(melOsc.frequency.get(),4));}
            if (time==Beat*92){
                melOut.stop();}
            if (time==Beat*93){
                melOut.start();
                melOsc.frequency.set(modulator(melOsc.frequency.get(),5));}
            if (time==Beat*94){
                melOsc.frequency.set(modulator(melOsc.frequency.get(),3));}
            if (time==Beat*95){
                melOsc.frequency.set(modulator(melOsc.frequency.get(),4));}
            
            
            if (time==Beat*96){
                melOut.stop();} 
            if (time==Beat*97){
                melOut.start();
                melOsc.frequency.set(modulator(keyFreq, 12));
                melOut.start();}
            if (time==Beat*98){
                melOsc.frequency.set(modulator(melOsc.frequency.get(),4));}
            if (time==Beat*99){
                melOsc.frequency.set(modulator(melOsc.frequency.get(),3));}
            if (time==Beat*101){
                melOsc.frequency.set(modulator(melOsc.frequency.get(),2));}
            if (time==Beat*103){
                melOsc.frequency.set(modulator(melOsc.frequency.get(),3));}
            
            
            if (time==Beat*105){
                melOut.stop();}            
            if (time==Beat*106){
               melOut.start();}
            if (time==Beat*110){
               melOut.stop();}
            
            if (time==Beat*112){
                melOut.start();}
            if (time==Beat*115){
                melOsc.frequency.set(modulator(keyFreq, 3));
                melSynth.sleepFor(Beat/10);
                melOut.stop();}
            if (time==Beat*116){
                melOut.start();}
            if (time==Beat*117){
                melOut.stop();}
            if (time==Beat*118){
                melOut.start();}
            
            
            
            if (time==Beat*120){
                melOsc.frequency.set(modulator(keyFreq, 24));}
            if (time==Beat*123){
                melOsc.frequency.set(modulator(keyFreq, 3));
                melSynth.sleepFor(Beat/10);
                melOut.stop();}
            if (time==Beat*124){
                melOut.start();}
            if (time==Beat*125){
                melOut.stop();}
            if (time==Beat*126){
                melOut.start();}
            
            
            
            if (time==Beat*128){
                melOut.stop();}
            if (time==Beat*123){
                melOut.start();
                melSynth.sleepFor(Beat/10);
                melOut.stop();}
            if (time==Beat*126){
                melOut.start();} 
            if (time==Beat*127){
                melOut.stop();
                lineOut.stop();
                lineOut2.stop();}
            
            if(time==Beat*128){
                osc.frequency.set(modulator(keyFreq,-16));
                osc2.frequency.set(modulator(keyFreq,-9));
                melOsc.frequency.set(modulator(keyFreq,0));
                melOut.start();
                lineOut.start();
                lineOut2.start();
                synth.sleepFor(Beat*4);
                osc.frequency.set(modulator(osc.frequency.get(),2));
                osc2.frequency.set(modulator(osc2.frequency.get(),2));
                melOsc.frequency.set(modulator(melOsc.frequency.get(),2));
                synth.sleepFor(Beat*2);
                osc.frequency.set(modulator(osc.frequency.get(),2));
                osc2.frequency.set(modulator(osc2.frequency.get(),2));
                melOsc.frequency.set(modulator(melOsc.frequency.get(),2));
                synth.sleepFor(Beat/2);
                osc.frequency.set(modulator(osc.frequency.get(),-2));
                osc2.frequency.set(modulator(osc2.frequency.get(),-2));
                melOsc.frequency.set(modulator(melOsc.frequency.get(),-2));
                synth.sleepFor(Beat/4);
                osc.frequency.set(modulator(osc.frequency.get(),-2));
                osc2.frequency.set(modulator(osc2.frequency.get(),-2));
                melOsc.frequency.set(modulator(melOsc.frequency.get(),-2));
                synth.sleepFor(Beat/6);
                osc.frequency.set(modulator(osc.frequency.get(),-2));
                osc2.frequency.set(modulator(osc2.frequency.get(),-2));
                melOsc.frequency.set(modulator(melOsc.frequency.get(),-2));
                synth.sleepFor(Beat/8);
                osc.frequency.set(modulator(osc.frequency.get(),-2));
                osc2.frequency.set(modulator(osc2.frequency.get(),-2));
                melOsc.frequency.set(modulator(melOsc.frequency.get(),-2));
                synth.sleepFor(Beat/8);
                osc.frequency.set(modulator(osc.frequency.get(),-2));
                osc2.frequency.set(modulator(osc2.frequency.get(),-2));
                melOsc.frequency.set(modulator(melOsc.frequency.get(),-2));
                synth.sleepFor(Beat/4);
                osc.frequency.set(modulator(osc.frequency.get(),-2));
                osc2.frequency.set(modulator(osc2.frequency.get(),-2));
                melOsc.frequency.set(modulator(melOsc.frequency.get(),-2));
                synth.sleepFor(Beat/2);
                osc.frequency.set(modulator(osc.frequency.get(),-2));
                osc2.frequency.set(modulator(osc2.frequency.get(),-2));
                melOsc.frequency.set(modulator(melOsc.frequency.get(),-2));
                synth.sleepFor(Beat);
                osc.frequency.set(modulator(osc.frequency.get(),-2));
                osc2.frequency.set(modulator(osc2.frequency.get(),-2));
                melOsc.frequency.set(modulator(melOsc.frequency.get(),-2));
                synth.sleepFor(Beat/9);
                for (int j=0; j<20; j++){
                    melOsc.frequency.set(modulator(melOsc.frequency.get(),-3));
                    synth.sleepFor(Beat/9);
                    melOsc.frequency.set(modulator(melOsc.frequency.get(),4));
                    synth.sleepFor(Beat/9);
                }
                for (int j=0; j<20; j++){
                    melOsc.frequency.set(modulator(melOsc.frequency.get(),-4));
                    synth.sleepFor(Beat/9);
                    melOsc.frequency.set(modulator(melOsc.frequency.get(),3));
                    synth.sleepFor(Beat/9);
                }
                
            }
            keyFreq2=modulator(melOsc.frequency.get(),5);
            if (time >= 152*Beat && time < 168*Beat){
                System.out.println(time/Beat);
                if (time%(Beat*split)==0){
                    osc.frequency.set(modulator(keyFreq2,-12));
                    osc2.frequency.set(modulator(keyFreq2, 7));
                    melOsc.frequency.set(modulator(keyFreq2,4));
                }
                if (time%(Beat*split)==1*Beat){
                    osc.frequency.set(modulator(osc.frequency.get(),3));
                    melOsc.frequency.set(modulator(melOsc.frequency.get(),-6));
                }
                if (time%(Beat*split)==2*Beat){
                    osc.frequency.set(modulator(osc.frequency.get(),5));
                    melOsc.frequency.set(modulator(melOsc.frequency.get(),2));
                    osc2.frequency.set(modulator(osc2.frequency.get(),1));
                }
                if (time%(Beat*split)==3*Beat){
                    osc.frequency.set(modulator(osc.frequency.get(),3));
                    melOsc.frequency.set(modulator(melOsc.frequency.get(),-6));
                }
                if (time%(Beat*split)==4*Beat){
                    osc.frequency.set(modulator(osc.frequency.get(),5));
                    melOsc.frequency.set(modulator(melOsc.frequency.get(),2));
                    osc2.frequency.set(modulator(osc2.frequency.get(),1));
                }
                if (time%(Beat*split)==5*Beat){
                    osc.frequency.set(modulator(osc.frequency.get(),3));
                    melOsc.frequency.set(modulator(melOsc.frequency.get(),-6));
                }
                if (time%(Beat*split)==6*Beat){
                    osc.frequency.set(modulator(osc.frequency.get(),5));
                    melOsc.frequency.set(modulator(melOsc.frequency.get(),2));
                    osc2.frequency.set(modulator(osc2.frequency.get(),1));
                }
                if (time%(Beat*split)==7*Beat){
                    
                }
                
            }
            
            
            if (time >= 168*Beat && time < 192*Beat){
                if (time%(Beat*split)==0){
                    osc.frequency.set(modulator(keyFreq2,-12));
                    osc2.frequency.set(modulator(keyFreq2, 7));
                    melOsc.frequency.set(modulator(keyFreq2,4));
                }
                if (time%(Beat*split)==1*Beat){
                   osc2.frequency.set(modulator(osc2.frequency.get(),1));
                }
                if (time%(Beat*split)==2*Beat){
                    osc2.frequency.set(modulator(osc2.frequency.get(),1));
                }
                if (time%(Beat*split)==3*Beat){
                    osc2.frequency.set(modulator(osc2.frequency.get(),1));
                }
                if (time%(Beat*split)==4*Beat){
                    osc2.frequency.set(modulator(osc2.frequency.get(),1));
                    osc.frequency.set(modulator(osc.frequency.get(),-2));
                    melOsc.frequency.set(modulator(melOsc.frequency.get(),-2));
                }
                if (time%(Beat*split)==5*Beat){
                    osc2.frequency.set(modulator(osc2.frequency.get(),1));
                }
                if (time%(Beat*split)==6*Beat){
                    osc2.frequency.set(modulator(osc2.frequency.get(),1));
                }
                if (time%(Beat*split)==7*Beat){
                    osc2.frequency.set(modulator(osc2.frequency.get(),1));
                }
            }
                
            
                if (time == 192*Beat){
                    System.out.println(time/Beat);
                    osc2.frequency.set(modulator(osc2.frequency.get(),1));
                } if (time == 193*Beat){
                    System.out.println(time/Beat);
                } if (time == 194*Beat){
                    System.out.println(time/Beat);
                    lineOut.stop();
                    melOut.stop();
                } 
                if (time>= 195*Beat && time< 199* Beat){
                    for (int k=0; k<16; k++){
                        System.out.println(time/Beat);
                        if (k%5==0){
                            osc2.frequency.set(modulator(osc2.frequency.get(),6));
                        } else if (k%5==1){
                            osc2.frequency.set(modulator(osc2.frequency.get(),-9));
                        } else if (k%5==2){
                            osc2.frequency.set(modulator(osc2.frequency.get(),12));
                        } else if (k%5==3){
                            osc2.frequency.set(modulator(osc2.frequency.get(),-15));
                        } else if (k%5==4){
                            osc2.frequency.set(modulator(osc2.frequency.get(),(k%5)));
                        }
                        if(k%8==1){
                            osc.frequency.set(modulator(keyFreq,-12));
                            lineOut.start();
                        } else if (k%8==3){
                            lineOut.stop();
                        } else if (k%8==5){
                            lineOut.start();
                        } else if (k%8==7){
                            lineOut.stop();
                        }
                        
                        if(k%7==6){
                            melOsc.frequency.set(modulator(keyFreq,6));
                            melOut.start();
                            melSynth.sleepFor(Beat/8);
                            melOut.stop();
                            melSynth.sleepFor(Beat/8);
                            melOut.start();
                            melSynth.sleepFor(Beat/8);
                            melOut.stop();
                            
                        }
                            
                        if (time<198*Beat){
                            synth2.sleepFor(Beat);
                        }
                        if (time>=198*Beat){
                            synth2.sleepFor(Beat/2);
                        }
                    }
                }
                
            if (time==200*Beat){
                lineOut.stop();
                lineOut2.stop();
                melOut.stop();
            }
            
            
             if(time==Beat*201){
                osc.frequency.set(modulator(keyFreq,-16));
                osc2.frequency.set(modulator(keyFreq,-9));
                melOsc.frequency.set(modulator(keyFreq,0));
                melOut.start();
                lineOut.start();
                lineOut2.start();
                synth.sleepFor(Beat*4);
                osc.frequency.set(modulator(osc.frequency.get(),2));
                osc2.frequency.set(modulator(osc2.frequency.get(),2));
                melOsc.frequency.set(modulator(melOsc.frequency.get(),2));
                synth.sleepFor(Beat*2);
                osc.frequency.set(modulator(osc.frequency.get(),2));
                osc2.frequency.set(modulator(osc2.frequency.get(),2));
                melOsc.frequency.set(modulator(melOsc.frequency.get(),2));
                synth.sleepFor(Beat/2);
                osc.frequency.set(modulator(osc.frequency.get(),-2));
                osc2.frequency.set(modulator(osc2.frequency.get(),-2));
                melOsc.frequency.set(modulator(melOsc.frequency.get(),-2));
                synth.sleepFor(Beat/4);
                osc.frequency.set(modulator(osc.frequency.get(),-2));
                osc2.frequency.set(modulator(osc2.frequency.get(),-2));
                melOsc.frequency.set(modulator(melOsc.frequency.get(),-2));
                synth.sleepFor(Beat/6);
                osc.frequency.set(modulator(osc.frequency.get(),-2));
                osc2.frequency.set(modulator(osc2.frequency.get(),-2));
                melOsc.frequency.set(modulator(melOsc.frequency.get(),-2));
                synth.sleepFor(Beat/8);
                osc.frequency.set(modulator(osc.frequency.get(),-2));
                osc2.frequency.set(modulator(osc2.frequency.get(),-2));
                melOsc.frequency.set(modulator(melOsc.frequency.get(),-2));
                synth.sleepFor(Beat/8);
                osc.frequency.set(modulator(osc.frequency.get(),-2));
                osc2.frequency.set(modulator(osc2.frequency.get(),-2));
                melOsc.frequency.set(modulator(melOsc.frequency.get(),-2));
                synth.sleepFor(Beat/4);
                osc.frequency.set(modulator(osc.frequency.get(),-2));
                osc2.frequency.set(modulator(osc2.frequency.get(),-2));
                melOsc.frequency.set(modulator(melOsc.frequency.get(),-2));
                synth.sleepFor(Beat/2);
                osc.frequency.set(modulator(osc.frequency.get(),-2));
                osc2.frequency.set(modulator(osc2.frequency.get(),-2));
                melOsc.frequency.set(modulator(melOsc.frequency.get(),-2));
                synth.sleepFor(Beat);
                osc.frequency.set(modulator(osc.frequency.get(),-2));
                osc2.frequency.set(modulator(osc2.frequency.get(),-2));
                melOsc.frequency.set(modulator(melOsc.frequency.get(),-2));
                synth.sleepFor(Beat*4);
                lineOut2.stop();
                melOut.stop();
            }
            
            
            
            
            
            if (time<128*Beat && time%(Beat*split)==0){
                if (time%(Beat*split*2)==0){
                    osc.frequency.set(baseFreq);
                    osc2.frequency.set(modulator(baseFreq, +24));
                } else{
                    osc.frequency.set(modulator(baseFreq,-2));
                    osc2.frequency.set(modulator(osc2.frequency.get(),+2));
                }
                lineOut2.start();
            } else if(time<128*Beat && time%(Beat*split)==1*Beat) {
                synth2.sleepUntil(time+((1.0/4.0)*Beat));
                lineOut2.stop();
              //  melOut.stop();
                //osc.frequency.set(modulator(osc.frequency.get(),2));
            } else if(time<128*Beat && time%(Beat*split)==2*Beat) {
                synth.sleepFor(Beat*1/9);
                lineOut.stop();
                //lineOut2.stop();
                //osc.frequency.set(modulator(osc.frequency.get(),2));
            } else if(time<128*Beat && time%(Beat*split)==3*Beat) {
                lineOut.start();
                osc.frequency.set(modulator(osc.frequency.get(),7));
            } else if(time<128*Beat && time%(Beat*split)==4*Beat) {
                osc.frequency.set(modulator(osc.frequency.get(),2));
            } else if(time<128*Beat && time%(Beat*split)==5*Beat) {
                osc.frequency.set(modulator(osc.frequency.get(),-9));
            } else if(time<128*Beat && time%(Beat*split)==6*Beat) {
                
                if (time%(Beat*split*2)==0){
                    osc.frequency.set(modulator(osc.frequency.get(),5));
                } else{
                    osc.frequency.set(modulator(osc.frequency.get(),9));
                }
                synth.sleepFor(Beat*90/100);
                lineOut.stop();
                synth.sleepFor(Beat*1/100);
                lineOut.start();
            } else if (time<128*Beat) {
                
                //lineOut2.start();
                System.out.println(time+"    "+Beat);
                synth2.sleepFor(Beat*2/3);
                //synth2.sleepUntil(time+((1.0/10.0)*Beat));
                lineOut2.stop();
                //osc.frequency.set(modulator(osc.frequency.get(),5));
            }
            time += Beat;
            try {
                //melSynth.sleepUntil(time+Beat);
                synth.sleepUntil(time);
            } catch (InterruptedException e){
                System.out.println("error");
            }
        }
    }

    
    public static double modulator(double frqncy, double halfSteps){
        return frqncy*Math.pow(2,(halfSteps/12));
    }
    
    public static double[] majorTriadArray(double frequency, int numNotes){
        double [] Note=new double[numNotes];
        for (int i=0; i<numNotes; i++){
            if (i%3==0){
                frequency=modulator(frequency, 4);
                Note[i]=frequency;
            } else if (i%3==1){
                frequency=modulator(frequency, 3);
                Note[i]=frequency;
            } else {
                frequency=modulator(frequency, 5);
                Note[i]=frequency;
            }
        }
        return Note;
    }
    
    public static void main(String[] args) {
        Looper bassline=new Looper(240);
        //Looper harm= new Looper(80.0);
        try {
               bassline.Loop1();
               //harm.Loop2();
            } catch (InterruptedException e){
                System.out.println("error");
            }
        
    }
}

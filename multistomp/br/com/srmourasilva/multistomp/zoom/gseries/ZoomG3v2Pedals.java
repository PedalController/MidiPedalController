package br.com.srmourasilva.multistomp.zoom.gseries;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;

import com.eclipsesource.json.Json;
import com.eclipsesource.json.JsonArray;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;

import br.com.srmourasilva.domain.multistomp.Effect;
import br.com.srmourasilva.domain.multistomp.Param;

public class ZoomG3v2Pedals {

	public static final ZoomG3v2Pedals instance = new ZoomG3v2Pedals();

	private HashMap<Integer, String> effectsName;

	private Map<String, JsonObject> jsonEffects;

	private ZoomG3v2Pedals() {
		this.effectsName = new HashMap<>();

		effectsName.put(0, "M-Filter");
		effectsName.put(1, "TheVibe");
		effectsName.put(2, "Z-Organ");
		effectsName.put(3, "Slicer");
		effectsName.put(4, "PhaseDly");
		effectsName.put(5, "FilterDly");
		effectsName.put(6, "PitchDly");
		effectsName.put(7, "StereoDly");
		effectsName.put(8, "BitCrush");
		effectsName.put(9, "Bomber");
		effectsName.put(10, "DuoPhase");
		effectsName.put(11, "MonoSynth");
		effectsName.put(12, "SeqFLTR");
		effectsName.put(13, "RndmFLTR");
		effectsName.put(14, "WarpPhase");
		effectsName.put(15, "TrgHldDly");
		effectsName.put(16, "Cho+Dly");
		effectsName.put(17, "Cho+Rev");
		effectsName.put(18, "Dly+Rev");
		effectsName.put(19, "Comp+Phsr");
		effectsName.put(20, "Comp+AWah");
		effectsName.put(21, "FLG+VCho");
		effectsName.put(22, "Comp+OD");
		effectsName.put(23, "Comp");
		effectsName.put(24, "RackComp");
		effectsName.put(25, "M Comp");
		effectsName.put(26, "SlowATTCK");
		effectsName.put(27, "ZNR");
		effectsName.put(28, "NoiseGate");
		effectsName.put(29, "DirtyGate");
		effectsName.put(30, "GraphicEQ");
		effectsName.put(31, "ParaEQ");
		effectsName.put(32, "CombFLTR");
		effectsName.put(33, "AutoWah");
		effectsName.put(34, "Resonance");
		effectsName.put(35, "Step");
		effectsName.put(36, "Cry");
		effectsName.put(37, "Octave");
		effectsName.put(38, "Tremolo");
		effectsName.put(39, "Phaser");
		effectsName.put(40, "RingMod");
		effectsName.put(41, "Chorus");
		effectsName.put(42, "Detune");
		effectsName.put(43, "VintageCE");
		effectsName.put(44, "StereoCho");
		effectsName.put(45, "Ensemble");
		effectsName.put(46, "VinFLNGR");
		effectsName.put(47, "DynaFLNGR");
		effectsName.put(48, "Vibrato");
		effectsName.put(49, "PitchSHFT");
		effectsName.put(50, "BendCho");
		effectsName.put(51, "MonoPitch");
		effectsName.put(52, "HPS");
		effectsName.put(53, "Delay");
		effectsName.put(54, "TapeEcho");
		effectsName.put(55, "ModDelay");
		effectsName.put(56, "AnalogDly");
		effectsName.put(57, "ReverseDL");
		effectsName.put(58, "MultiTapD");
		effectsName.put(59, "DynaDelay");
		effectsName.put(60, "Hall");
		effectsName.put(61, "Room");
		effectsName.put(62, "TiledRoom");
		effectsName.put(63, "Spring");
		effectsName.put(64, "Arena");
		effectsName.put(65, "EarlyRef");
		effectsName.put(66, "Air");
		effectsName.put(67, "PedalVx");
		effectsName.put(68, "PedalCry");
		effectsName.put(69, "PDL Pitch");
		effectsName.put(70, "PDL MnPit");
		effectsName.put(71, "Booster");
		effectsName.put(72, "OverDrive");
		effectsName.put(73, "T Scream");
		effectsName.put(74, "Governor");
		effectsName.put(75, "Dist+");
		effectsName.put(76, "Dist 1");
		effectsName.put(77, "Squeak");
		effectsName.put(78, "FuzzSmile");
		effectsName.put(79, "GreatMuff");
		effectsName.put(80, "MetalWRLD");
		effectsName.put(81, "HotBox");
		effectsName.put(82, "Z Wild");
		effectsName.put(83, "Lead");
		effectsName.put(84, "ExtremeDS");
		effectsName.put(85, "Aco.Sim");
		effectsName.put(86, "Z Clean");
		effectsName.put(87, "Z MP1");
		effectsName.put(88, "Z Bottom");
		effectsName.put(89, "Z Dream");
		effectsName.put(90, "Z Scream");
		effectsName.put(91, "Z Neos");
		effectsName.put(92, "FD COMBO");
		effectsName.put(93, "VX COMBO");
		effectsName.put(94, "US BLUES");
		effectsName.put(95, "BG CRUNCH");
		effectsName.put(96, "HW STACK");
		effectsName.put(97, "TANGERINE");
		effectsName.put(98, "MS CRUNCH");
		effectsName.put(99, "MS DRIVE");
		effectsName.put(100, "BG DRIVE");
		effectsName.put(101, "DZ DRIVE");
		effectsName.put(102, "TW ROCK");
		effectsName.put(103, "MATCH 30");
		effectsName.put(104, "FD VIBRO");
		effectsName.put(105, "HD Reverb");
		effectsName.put(106, "Flanger");
		effectsName.put(107, "None");
		effectsName.put(108, "TONE CITY");
		effectsName.put(109, "B-BREAKER");
		effectsName.put(110, "BGN DRIVE");
		effectsName.put(111, "DELUXE-R");
		effectsName.put(112, "ALIEN");
		effectsName.put(113, "REVO-1");
		effectsName.put(114, "CAR DRIVE");
		effectsName.put(115, "MS 1959");
		effectsName.put(116, "VX JMI");
		
		String fileName = System.getProperty("user.dir") + File.separator + "lib" + File.separator + "zoom-G3X-defaults.json";
		try {
			this.jsonEffects = readJsonEffectsOf(fileName);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private Map<String, JsonObject> readJsonEffectsOf(String fileName) throws FileNotFoundException, IOException {
		Reader reader = new FileReader(fileName);
		JsonObject value = Json.parse(reader).asObject();
		reader.close();

		JsonArray effects = value.get("Effect").asArray();
		
		Map<String, JsonObject> jsonEffects = new HashMap<>();
		for (JsonValue effect : effects)
			jsonEffects.put(effect.asObject().get("name").asString(), effect.asObject());

		return jsonEffects;
	}

	public Effect getEffect(int idEffect) {
		Effect effect = new Effect(idEffect, this.effectsName.get(idEffect));
		
		JsonArray controllers = jsonEffects.get(effect.getName()).get("Ctrl").asArray();

		for (JsonValue controller : controllers)
			effect.addParam(generateParam(controller.asObject()));

		return effect;
	}

	

	private static Param generateParam(JsonObject param) {
		return new Param(
			param.get("name").asString(),
			0,
			Integer.parseInt(param.get("max").asString()),
			Integer.parseInt(param.get("default").asString()),
			1
		);
	}

	public static void main(String[] args) throws IOException {
		System.out.println(instance.getEffect(116));
	}
}

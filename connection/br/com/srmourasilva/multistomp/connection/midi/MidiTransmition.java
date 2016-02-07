package br.com.srmourasilva.multistomp.connection.midi;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import javax.sound.midi.MidiDevice;
import javax.sound.midi.MidiDevice.Info;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;

import br.com.srmourasilva.architecture.exception.DeviceNotFoundException;

public abstract class MidiTransmition {
	
	private MidiDevice device;

	public MidiTransmition(MidiDevice device) {
		this.device = device;
	}

	public void start() throws MidiUnavailableException {
		device.open();
	}

	public void stop() {
    	device.close();
    }
	
	protected MidiDevice device() {
		return device;
	}

	///////////////////////////////////////////////////

	public static MidiSender getSenderOf(String name) throws DeviceNotFoundException {
		List<Info> infos = findInfoDevicesBy(name);
		Optional<MidiDevice> device = getDevice(infos, dev -> dev.getMaxReceivers() != 0);

		if (!device.isPresent())
			throw new DeviceNotFoundException("Midi output device not found for: " + name);

		return new MidiSender(device.get());
	}
	

	public static MidiReader getReaderOf(String name) throws DeviceNotFoundException {
		List<Info> infos = findInfoDevicesBy(name);
		Optional<MidiDevice> device = getDevice(infos, dev -> dev.getMaxTransmitters() != 0);

		if (!device.isPresent())
			throw new DeviceNotFoundException("Midi input device not found for: " + name);

		return new MidiReader(device.get());
	}

	private static Optional<MidiDevice> getDevice(List<Info> infos, Predicate<MidiDevice> condition) {
		MidiDevice device;

		for (Info deviceInfo : infos) {
			try {
				device = MidiSystem.getMidiDevice(deviceInfo);
			} catch (MidiUnavailableException e) {
				continue;
			}

			if (condition.test(device))
				return Optional.of(device);
		}

		return Optional.empty();
	}

	/** @return all devices that corresponding
	 *  the PedalType
	 */
	public static List<Info> findInfoDevicesBy(String name) {
		List<Info> devices = new ArrayList<Info>();

		Info[] infos = MidiSystem.getMidiDeviceInfo();
		Info device;

		for (int i=0; i<infos.length; i++) {
			device = infos[i];

			if (device.getName().contains(name))
				devices.add(device);
		}

		return devices;
	}
}

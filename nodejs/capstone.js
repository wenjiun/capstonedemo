// requires bleno module
var bleno = require('bleno');

// the value that can be read and written from Android
var value = 1;

// setup BLE GATT characteristics with descriptors
var CapstoneCharacteristic = bleno.Characteristic;

var capstoneCharacteristic = new CapstoneCharacteristic({
	uuid: '13333333333333333333333333330001',
	properties: ['read', 'write'],
	descriptors: [
      		new bleno.Descriptor({
        		uuid: '2901',
        		value: 'Capstone demo string.'
      		})
	],
	onReadRequest: function(offset, callback) {
  		if (offset) {
    			callback(this.RESULT_ATTR_NOT_LONG, null);
  		} else {
    			var data = new Buffer(1);
    			data.writeUInt8(value, 0);
			console.log('Data read: ' + value);
    			callback(this.RESULT_SUCCESS, data);
  		}
	},
	onWriteRequest: function(data, offset, withoutResponse, callback) {
  		if (offset) {
    			callback(this.RESULT_ATTR_NOT_LONG);
  		} else if (data.length !== 1) {
    			callback(this.RESULT_INVALID_ATTRIBUTE_LENGTH);
  		} else {
    			value = data.readUInt8(0);
			console.log('Data written: ' + value);
			callback(this.RESULT_SUCCESS);
		}
  	}
});

// setup BLE GATT services 
var CapstoneService = bleno.PrimaryService;

var capstoneService = new CapstoneService({
        uuid: '13333333333333333333333333333337',
        characteristics: [
            capstoneCharacteristic
        ]
});

// start BLE advertising if USB BLE is on
bleno.on('stateChange', function(state) {
  if (state === 'poweredOn') {
    bleno.startAdvertising('Capstone Project', ['13333333333333333333333333333337'], function(err) {
      if (err) {
        console.log(err);
      }
    });
  }
  else {
    bleno.stopAdvertising();
  }
});

// start BLE Gatt services 
bleno.on('advertisingStart', function(err) {
  if (!err) {
    console.log('Advertising...');
    bleno.setServices([
      capstoneService
    ]);
  }
});

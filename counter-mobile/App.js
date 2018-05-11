import React from 'react';
import {  Button, StyleSheet, View } from 'react-native';
import Config from 'react-native-config';

export default class App extends React.Component {
	
  _onPressButton() {
	  
   // fetch(Config.SERVER_URL+'/click', {
//	method: 'POST',
	// mode: 'no-cors'
//	}).catch(function(error) {
//console.log('There has been a problem with your fetch operation: ' + error.message);
  // throw error;
 //});
  }ASDSADSADSA
  render() {aDSASD
	  console.log(Config.SERVER_URL);
	  console.log(Expo.Constants.manifest.server);
	  console.log(process.env['SERVER_URL']);
    return (
    <View style={styles.container}>
        <View style={styles.buttonContainer}>
          <Button
            onPress={this._onPressButton}
            title="Click!"
          />
        </View>
      </View>
    );
  }
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: '#fff',
    alignItems: 'center',
    justifyContent: 'center',
  },
});

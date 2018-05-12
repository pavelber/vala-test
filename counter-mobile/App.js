import React from 'react';
import {  Button, StyleSheet, View } from 'react-native';
import Config from 'react-native-config';

export default class App extends React.Component {
	
  _onPressButton() {
	 
    fetch(Config.SERVER_URL+'/click', {
    method: 'POST',
	 mode: 'no-cors'
	}).catch(function(error) {
		console.log(error);
	 throw error;
 });
  }
  render() {
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

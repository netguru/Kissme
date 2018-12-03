//
//  MainViewController.swift
//  iOSApp
//

import UIKit
import app
import keychainwrapper

class MainViewController: UIViewController, MainView {
    
    @IBOutlet weak var passwordTextField: UITextField!
    
    var presenter: MainPresenter {
        return MainPresenter()
    }
    
    
    override func viewDidLoad() {
        super.viewDidLoad()
        presenter.attachView(view: self)
    }
    
    @IBAction func saveButtonPressed(_ sender: Any) {
        guard let passwordText = passwordTextField.text, !passwordText.isEmpty else {
            return
        }
        
        presenter.addNewToDoElement(item: passwordText)
    }
    
    @IBAction func showPasswordsButtonPressed(_ sender: Any) {
        
    }
    
    func showElementAddedInfo() {
//        let alertController = UIAlertController(title: "Success!", message: "Added password to keychain!", preferredStyle: .alert)
//        self.present(alertController, animated: true, completion: nil)
    }
    
    func showToDoList(toDoList: [String]) {
//        print("showToDoListCalled")
    }
    
}


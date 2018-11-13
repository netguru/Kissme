import UIKit
import app

class MainViewController: UIViewController {
    
    private lazy var view: MainView?
    
    override func viewDidLoad() {
        super.viewDidLoad()
        setupCallbacks()
        label.text = Proxy().proxyHello()
    }
    
    override func loadView() {
        view = MainView()
    }
    
    private func setupCallbacks() {
//        let key = view?.keyNameTextField.text
//        let value = view?.valueTextField.text
        view?.saveButton.addTarget(self, action: #selector(saveButtonPressed), for: .touchUpInside)
        
    }

    @objc private func saveButtonPressed() {
        let keysViewController = KeysViewController()
        navigationController?.pushViewController(keysViewController, animated: true)
    }
}



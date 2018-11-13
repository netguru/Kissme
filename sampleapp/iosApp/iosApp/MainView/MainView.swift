//
//  MainView.swift
//  iosApp
//

import UIKit

final class MainView: UIView {
    
    let keyNameTextField: UITextField = {
        let textField = UITextField()
        textField.placeholder = "Key name"
        return textField
    }()
    
    let valueTextField: UITextField = {
        let textField = UITextField()
        textField.placeholder = "value"
        return textField
    }()
    
    let saveButton: UIButton = {
        let button = UIButton(type: .system)
        return button
    }
    
    init() {
        super.init(frame: .zero)
        setupContraints()
    }
    
    private func setupContraints() {
        [keyNameTextField, valueTextField, saveButton].forEach { subview in
            subview.centerXAnchor.constraint(equalTo: self.centerXAnchor)
            subview.widthAnchor.constraint(equalToConstant: 200)
            
        }

        keyNameTextField.topAnchor.constraint(equalTo: self.topAnchor, constant: 140)
        valueTextField.topAnchor.constraint(equalTo: keyNameTextField.bottomAnchor, constant: 20)
        saveButton.topAnchor.constraint(equalTo: valueTextField.bottomAnchor, constant: 20)       
    }
}

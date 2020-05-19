using design_client.Service;
using MaterialDesignThemes.Wpf;
using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Text;
using System.Windows;
using System.Windows.Controls;
using System.Windows.Data;
using System.Windows.Documents;
using System.Windows.Input;
using System.Windows.Media;
using System.Windows.Media.Imaging;
using System.Windows.Navigation;
using System.Windows.Shapes;

namespace design_client.Views.Components
{
    /// <summary>
    /// LoginControl.xaml 的交互逻辑
    /// </summary>
    public partial class LoginControl : UserControl,INotifyPropertyChanged
    {
        private DialogHost host;

        private string _uh = "";
        private string _ph = "";

        public event PropertyChangedEventHandler? PropertyChanged;

        public string UsernameHelper { 
            get => _uh;
            set => OnPropertyChanged("UsernameHelper"); 
        }

        public string PasswordHelper { 
            get=> _ph; 
            set=> OnPropertyChanged("PasswordHelper"); 
        }

        public string Username { get; set; } = "";
        public string Password { get; set; } = "";

        public UserService userService = UserService.GetInstance();

        public LoginControl(DialogHost host)
        {
            InitializeComponent();
            this.host = host;
        }

        protected void OnPropertyChanged(string pname)
        {
            this.PropertyChanged?.Invoke(this,new PropertyChangedEventArgs(pname));
        }

        private async void Login_Click(object sender, RoutedEventArgs e)
        {
            if (string.IsNullOrEmpty(Username)||string.IsNullOrEmpty(Password))
            {
                MessageBox.Show("账号和密码不能为空");
            }
            var user = await userService.Login(Username,Password);
            host.IsOpen = false;
        }

        private void Cancle_Click(object sender, RoutedEventArgs e)
        {
            host.IsOpen = false;
        }
    }
}

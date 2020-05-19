using design_client.Context;
using design_client.Model;
using design_client.Views;
using design_client.Views.Components;
using MaterialDesignThemes.Wpf;
using System;
using System.Collections.Generic;
using System.Diagnostics;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows;
using System.Windows.Controls;
using System.Windows.Data;
using System.Windows.Documents;
using System.Windows.Input;
using System.Windows.Media;
using System.Windows.Media.Imaging;
using System.Windows.Navigation;
using System.Windows.Shapes;

namespace design_client
{
    /// <summary>
    /// Interaction logic for MainWindow.xaml
    /// </summary>
    public partial class MainWindow : Window
    {
        public bool ShowLoginBtn { get; set; } = true;

        private SecurityHandler<User> loginHandler = user =>
        {
            if (SecurityContext.IsLogin())
            {
                
            } else
            {

            }
        };

        public MainWindow()
        {
            InitializeComponent();
            this.DataContext = this;
            SecurityContext.Logined += loginHandler;
            SecurityContext.Logouted += loginHandler;
        }

        private void ControlTab_SelectionChanged(object sender, SelectionChangedEventArgs e)
        {
            var sel = this.ControlTab.SelectedIndex;
            if (sel >= 0)
            {
                this.ControlTabProvider?.UnselectAll();
            }
            if (this.ControlContent != null && sel >= 0 && sel < this.ControlContent.Items.Count)
            {
                this.ControlContent.SelectedIndex = sel;
            }
        }

        private void ControlTab_SelectionChanged_Provider(object sender, SelectionChangedEventArgs e)
        {
            var sel = this.ControlTabProvider.SelectedIndex;
            if (sel >= 0)
            {
                this.ControlTab.UnselectAll();
            }
            var offset = this.ControlTab.Items.Count;
            if (this.ControlContent != null && sel >= 0 && sel + offset < this.ControlContent.Items.Count)
            {
                this.ControlContent.SelectedIndex = offset + sel;
            }
        }

        private void ShowLoginBtn_Click(object sender, RoutedEventArgs e)
        {
            this.TopDialog.ShowDialog(new LoginControl(this.TopDialog));
        }
    }
}

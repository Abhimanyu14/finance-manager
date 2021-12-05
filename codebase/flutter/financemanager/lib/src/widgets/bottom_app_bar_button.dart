import 'package:flutter/material.dart';

class BottomAppBarButton extends StatelessWidget {
  const BottomAppBarButton({
    Key key,
    this.icon,
    @required this.onPressed,
    @required this.tooltip,
  }) : super(key: key);

  final IconData icon;
  final Function onPressed;
  final String tooltip;

  @override
  Widget build(BuildContext context) {
    return IconButton(
      iconSize: 30.0,
      padding: const EdgeInsets.symmetric(
        horizontal: 16.0,
        vertical: 12.0,
      ),
      icon: Icon(icon),
      color: Colors.white,
      onPressed: onPressed,
      tooltip: tooltip,
    );
  }
}
